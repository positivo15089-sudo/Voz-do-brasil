package br.com.vozdobrasil.studio.data.remote

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import br.com.vozdobrasil.studio.domain.model.SpeakerProfile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.File
import java.util.Locale
import java.util.UUID
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * Repositório Modular de IA e Síntese de Voz (TTS) do VOZ DO BRASIL.
 * - Integra com o Backend Seguro (ElevenLabs, Google Cloud TTS, Google Gemini)
 * - Integra com o Motor Real Nativo do Android (TextToSpeech.synthesizeToFile)
 * - Nunca simula geração falsa: verifica disponibilidade das vozes pt-BR e status das APIs.
 */
class TtsAndAiRepository(private val context: Context) {

    private val httpClient = OkHttpClient()
    private var nativeTts: TextToSpeech? = null
    var isNativePtBrAvailable: Boolean = false
        private set

    fun initializeNativeTts(onReady: (Boolean, List<String>) -> Unit) {
        nativeTts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                val ptBr = Locale("pt", "BR")
                val res = nativeTts?.setLanguage(ptBr)
                isNativePtBrAvailable = res != TextToSpeech.LANG_MISSING_DATA && res != TextToSpeech.LANG_NOT_SUPPORTED
                val availableVoices = nativeTts?.voices
                    ?.filter { it.locale.language == "pt" }
                    ?.map { it.name } ?: emptyList()
                onReady(isNativePtBrAvailable, availableVoices)
            } else {
                onReady(false, emptyList())
            }
        }
    }

    /**
     * Sintetiza arquivo WAV real utilizando o motor nativo Android configurado com o perfil do locutor,
     * aplicando dicionário de pronúncia personalizada e pausas.
     */
    suspend fun synthesizeWithNativeEngine(
        rawText: String,
        speaker: SpeakerProfile,
        customPronunciations: Map<String, String>,
        outputFile: File
    ): Result<File> = withContext(Dispatchers.IO) {
        val tts = nativeTts ?: return@withContext Result.failure(
            IllegalStateException("Motor nativo de síntese de voz ainda não inicializado.")
        )
        if (!isNativePtBrAvailable) {
            return@withContext Result.failure(
                IllegalStateException("Nenhuma voz em Português Brasileiro (pt-BR) encontrada no dispositivo. Instale o pacote de voz nas configurações do Android ou conecte a API ElevenLabs/Google TTS.")
            )
        }

        var processedText = rawText
        customPronunciations.forEach { (original, phonetic) ->
            processedText = processedText.replace(Regex(Regex.escape(original), RegexOption.IGNORE_CASE), phonetic)
        }

        tts.setPitch(speaker.pitch.coerceIn(0.5f, 2.0f))
        tts.setSpeechRate(speaker.speed.coerceIn(0.5f, 2.0f))

        suspendCancellableCoroutine { cont ->
            val utteranceId = UUID.randomUUID().toString()
            tts.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(id: String?) {}
                override fun onDone(id: String?) {
                    if (id == utteranceId && cont.isActive) {
                        cont.resume(Result.success(outputFile))
                    }
                }
                @Deprecated("Deprecated in Java")
                override fun onError(id: String?) {
                    if (id == utteranceId && cont.isActive) {
                        cont.resume(Result.failure(RuntimeException("Erro ao sintetizar áudio no motor nativo.")))
                    }
                }
            })
            tts.synthesizeToFile(processedText, null, outputFile, utteranceId)
        }
    }

    /**
     * Sintetiza áudio via Backend Seguro (ElevenLabs ou Google Cloud TTS).
     * Caso o Backend não esteja conectado, retorna erro explícito orientando o usuário.
     */
    suspend fun synthesizeViaSecureBackend(
        backendBaseUrl: String,
        provider: String,
        text: String,
        speaker: SpeakerProfile,
        outputFile: File
    ): Result<File> = withContext(Dispatchers.IO) {
        if (backendBaseUrl.isBlank()) {
            return@withContext Result.failure(
                IllegalStateException("Backend seguro não configurado. Conecte a URL do servidor na tela 'Gerenciamento de API' para utilizar o provedor $provider.")
            )
        }

        try {
            val payload = JSONObject().apply {
                put("text", text)
                put("provider", provider)
                put("voiceId", speaker.elevenLabsVoiceId ?: speaker.googleVoiceName)
                put("stability", speaker.stability)
                put("similarityBoost", speaker.intensity)
                put("style", speaker.expressiveness)
                put("speed", speaker.speed)
            }
            val request = Request.Builder()
                .url("${backendBaseUrl.trimEnd('/')}/api/v1/tts/synthesize")
                .post(payload.toString().toRequestBody("application/json".toMediaType()))
                .build()

            httpClient.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    val bodyStr = response.body?.string() ?: "Erro HTTP ${response.code}"
                    return@withContext Result.failure(RuntimeException("Provedor $provider indisponível: $bodyStr"))
                }
                val bytes = response.body?.bytes() ?: return@withContext Result.failure(RuntimeException("Resposta de áudio vazia."))
                outputFile.writeBytes(bytes)
                Result.success(outputFile)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
