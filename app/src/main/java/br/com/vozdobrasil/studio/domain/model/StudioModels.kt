package br.com.vozdobrasil.studio.domain.model

enum class SpeakerGender(val label: String) {
    MALE("Masculino"),
    FEMALE("Feminino")
}

data class SpeakerProfile(
    val id: String,
    val name: String,
    val category: String,
    val gender: SpeakerGender,
    val description: String,
    val pitch: Float,
    val speed: Float,
    val intensity: Float,
    val stability: Float,
    val expressiveness: Float,
    val defaultEmotion: String,
    val bassBoostDb: Float,
    val presenceBoostDb: Float,
    val elevenLabsVoiceId: String? = null,
    val googleVoiceName: String = "pt-BR-Neural2-B",
    val isCustom: Boolean = false
)

object DefaultSpeakersCatalog {
    val maleSpeakers = listOf(
        SpeakerProfile("m_comercial_premium", "Locutor Comercial Premium", "Comercial", SpeakerGender.MALE, "Voz grave, encorpada e impactante para grandes marcas.", 0.78f, 1.0f, 0.90f, 0.85f, 0.80f, "Impactante", 6.0f, 3.5f, "pNInz6obpgDQGcFmaJgB", "pt-BR-Neural2-B"),
        SpeakerProfile("m_spot", "Locutor de SPOT", "SPOT", SpeakerGender.MALE, "Voz muito grave, potente e profissional de estúdio FM.", 0.70f, 1.04f, 0.95f, 0.82f, 0.88f, "Potente", 8.0f, 4.5f, "VR6AewLTigWG4xSOukaG", "pt-BR-Wavenet-B"),
        SpeakerProfile("m_radio", "Locutor de Rádio", "Rádio FM", SpeakerGender.MALE, "Estilo radialista brasileiro vibrante e comunicativo.", 0.88f, 1.08f, 0.92f, 0.78f, 0.90f, "Vibrante", 4.5f, 4.0f, null, "pt-BR-Neural2-B"),
        SpeakerProfile("m_ofertas", "Locutor de Ofertas", "Varejo", SpeakerGender.MALE, "Voz animada, ágil e altamente persuasiva para promoções.", 0.94f, 1.22f, 0.98f, 0.75f, 0.95f, "Entusiasmado", 3.5f, 5.0f, null, "pt-BR-Neural2-B"),
        SpeakerProfile("m_cinema", "Locutor Cinematográfico", "Trailer / Cinema", SpeakerGender.MALE, "Voz profunda, misteriosa e dramática com sub-graves intensos.", 0.66f, 0.88f, 0.92f, 0.90f, 0.85f, "Dramático", 9.0f, 3.0f, null, "pt-BR-Wavenet-B"),
        SpeakerProfile("m_institucional", "Locutor Institucional", "Corporativo", SpeakerGender.MALE, "Voz séria, elegante e confiável para manifestos e empresas.", 0.84f, 0.96f, 0.80f, 0.92f, 0.70f, "Sério / Elegante", 4.0f, 2.5f, null, "pt-BR-Neural2-B"),
        SpeakerProfile("m_jovem", "Locutor Jovem", "Digital / Redes", SpeakerGender.MALE, "Voz moderna, natural e descontraída para Reels e TikTok.", 1.04f, 1.10f, 0.82f, 0.76f, 0.86f, "Descontraído", 1.5f, 3.5f, null, "pt-BR-Neural2-B"),
        SpeakerProfile("m_sertanejo", "Locutor Sertanejo", "Rodeio & Shows", SpeakerGender.MALE, "Estilo comercial de arena de rodeio e eventos sertanejos.", 0.74f, 1.05f, 0.98f, 0.80f, 0.96f, "Arena / Vibrante", 7.5f, 4.5f, null, "pt-BR-Wavenet-B"),
        SpeakerProfile("m_esportivo", "Narrador Esportivo", "Esportes", SpeakerGender.MALE, "Voz energética, explosiva e vibrante de transmissão ao vivo.", 0.92f, 1.18f, 0.99f, 0.72f, 0.98f, "Eufórico", 4.0f, 5.5f, null, "pt-BR-Neural2-B"),
        SpeakerProfile("m_religioso", "Narrador Religioso", "Fé & Reflexão", SpeakerGender.MALE, "Voz serena, acolhedora, pausada e profundamente expressiva.", 0.80f, 0.86f, 0.75f, 0.94f, 0.82f, "Sereno / Devocional", 5.5f, 2.0f, null, "pt-BR-Neural2-B")
    )

    val femaleSpeakers = listOf(
        SpeakerProfile("f_comercial_premium", "Locutora Comercial Premium", "Comercial", SpeakerGender.FEMALE, "Voz feminina encorpada, firme e sofisticada para marcas líderes.", 0.96f, 1.0f, 0.88f, 0.86f, 0.84f, "Impactante", 3.5f, 4.0f, "EXAVITQu4vr4xnSDxMaL", "pt-BR-Neural2-A"),
        SpeakerProfile("f_radio", "Locutora de Rádio", "Rádio FM", SpeakerGender.FEMALE, "Carismática, alto-astral e com presença marcante de estúdio FM.", 1.04f, 1.08f, 0.90f, 0.80f, 0.90f, "Alegre", 2.5f, 4.5f, null, "pt-BR-Neural2-C"),
        SpeakerProfile("f_spot", "Locutora de SPOT", "SPOT", SpeakerGender.FEMALE, "Potente, incisiva e dinâmica para chamadas publicitárias rápidas.", 0.98f, 1.06f, 0.94f, 0.82f, 0.88f, "Persuasiva", 3.5f, 4.5f, null, "pt-BR-Neural2-A"),
        SpeakerProfile("f_institucional", "Locutora Institucional", "Corporativo", SpeakerGender.FEMALE, "Postura executiva, serena e articulada para vídeos institucionais.", 0.94f, 0.95f, 0.78f, 0.92f, 0.72f, "Elegante", 2.5f, 3.0f, null, "pt-BR-Neural2-C"),
        SpeakerProfile("f_jovem", "Locutora Jovem", "Digital / Redes", SpeakerGender.FEMALE, "Moderna, espontânea e conectada com o público de redes sociais.", 1.12f, 1.12f, 0.85f, 0.75f, 0.88f, "Descontraída", 1.0f, 4.0f, null, "pt-BR-Neural2-A"),
        SpeakerProfile("f_elegante", "Locutora Elegante", "Luxo & Moda", SpeakerGender.FEMALE, "Timbre aveludado e refinado para joalherias, estética e clínicas.", 0.92f, 0.92f, 0.76f, 0.94f, 0.78f, "Sofisticada", 3.0f, 2.5f, null, "pt-BR-Neural2-C"),
        SpeakerProfile("f_emocional", "Locutora Emocional", "Homenagens", SpeakerGender.FEMALE, "Interpretação calorosa, afetiva e tocante para mensagens especiais.", 1.00f, 0.90f, 0.82f, 0.78f, 0.95f, "Emocionada", 2.5f, 3.0f, null, "pt-BR-Neural2-A"),
        SpeakerProfile("f_cinema", "Narradora Cinematográfica", "Documentário", SpeakerGender.FEMALE, "Profunda, envolvente e dramática para narrativas épicas.", 0.88f, 0.88f, 0.88f, 0.90f, 0.86f, "Dramática", 4.5f, 3.0f, null, "pt-BR-Neural2-C"),
        SpeakerProfile("f_ofertas", "Locutora de Ofertas", "Varejo", SpeakerGender.FEMALE, "Vibrante, rápida e vendedora para supermercados e magazines.", 1.08f, 1.20f, 0.96f, 0.76f, 0.94f, "Entusiasmada", 2.0f, 5.0f, null, "pt-BR-Neural2-A"),
        SpeakerProfile("f_suave", "Voz Feminina Suave", "Bem-Estar / URA", SpeakerGender.FEMALE, "Tranquila, cristalina e acolhedora para meditação e atendimento.", 1.02f, 0.88f, 0.68f, 0.96f, 0.70f, "Suave", 2.0f, 2.0f, null, "pt-BR-Neural2-C")
    )

    val allSpeakers: List<SpeakerProfile> = maleSpeakers + femaleSpeakers
}
