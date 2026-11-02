package br.com.vozdobrasil.studio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.vozdobrasil.studio.domain.model.DefaultSpeakersCatalog
import br.com.vozdobrasil.studio.domain.model.SpeakerProfile
import br.com.vozdobrasil.studio.ui.theme.*

enum class AppScreen(val title: String) {
    SPLASH("Splash"),
    LOGIN("Acesso ao Estúdio"),
    HOME("Painel Principal"),
    VOICE_STUDIO("Estúdio de Voz"),
    SPEAKERS_LIBRARY("Biblioteca de Locutores"),
    CREATE_MY_VOICE("Criar Minha Voz"),
    SPOT_GENERATOR("Gerador de SPOTS Profissionais"),
    AUDIO_STUDIO("Estúdio de Produção de Áudio"),
    VOICE_EDITOR("Editor de Locução"),
    PROJECTS_LIBRARY("Meus Projetos & Biblioteca"),
    HISTORY("Histórico de Produções"),
    API_MANAGEMENT("Gerenciamento de API & Cotas"),
    SETTINGS("Configurações do Estúdio")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VozDoBrasilTheme {
                VozDoBrasilMainApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VozDoBrasilMainApp() {
    var currentScreen by remember { mutableStateOf(AppScreen.HOME) }
    var selectedSpeaker by remember { mutableStateOf(DefaultSpeakersCatalog.maleSpeakers.first()) }
    var studioScript by remember {
        mutableStateOf("Atenção Brasil! Chegou o grande feirão de ofertas com descontos reais para você economizar de verdade. Venha conferir hoje mesmo!")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("VOZ DO BRASIL", color = StudioBrightGold, fontWeight = FontWeight.ExtraBold, fontSize = 18.sp)
                        Text("Sua voz. Sua criatividade. Sua produção.", color = StudioTextSecondary, fontSize = 11.sp)
                    }
                },
                actions = {
                    IconButton(onClick = { currentScreen = AppScreen.API_MANAGEMENT }) {
                        Icon(Icons.Default.Key, contentDescription = "Gerenciar APIs", tint = StudioGold)
                    }
                    IconButton(onClick = { currentScreen = AppScreen.PROJECTS_LIBRARY }) {
                        Icon(Icons.Default.FolderSpecial, contentDescription = "Meus Projetos", tint = StudioGold)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = StudioGraphite)
            )
        },
        bottomBar = {
            NavigationBar(containerColor = StudioGraphite) {
                NavigationBarItem(
                    selected = currentScreen == AppScreen.HOME,
                    onClick = { currentScreen = AppScreen.HOME },
                    icon = { Icon(Icons.Default.Home, contentDescription = null) },
                    label = { Text("Início") }
                )
                NavigationBarItem(
                    selected = currentScreen == AppScreen.VOICE_STUDIO,
                    onClick = { currentScreen = AppScreen.VOICE_STUDIO },
                    icon = { Icon(Icons.Default.Mic, contentDescription = null) },
                    label = { Text("Locução") }
                )
                NavigationBarItem(
                    selected = currentScreen == AppScreen.SPOT_GENERATOR,
                    onClick = { currentScreen = AppScreen.SPOT_GENERATOR },
                    icon = { Icon(Icons.Default.Campaign, contentDescription = null) },
                    label = { Text("SPOT IA") }
                )
                NavigationBarItem(
                    selected = currentScreen == AppScreen.AUDIO_STUDIO,
                    onClick = { currentScreen = AppScreen.AUDIO_STUDIO },
                    icon = { Icon(Icons.Default.GraphicEq, contentDescription = null) },
                    label = { Text("Mixagem") }
                )
                NavigationBarItem(
                    selected = currentScreen == AppScreen.SPEAKERS_LIBRARY,
                    onClick = { currentScreen = AppScreen.SPEAKERS_LIBRARY },
                    icon = { Icon(Icons.Default.RecordVoiceOver, contentDescription = null) },
                    label = { Text("Vozes") }
                )
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(StudioBlack)
                .padding(padding)
                .padding(16.dp)
        ) {
            when (currentScreen) {
                AppScreen.HOME -> HomeDashboardScreen(onNavigate = { currentScreen = it })
                AppScreen.SPEAKERS_LIBRARY -> SpeakersCatalogScreen(
                    selected = selectedSpeaker,
                    onSelect = {
                        selectedSpeaker = it
                        currentScreen = AppScreen.VOICE_STUDIO
                    }
                )
                else -> StudioQuickSummaryCard(
                    screen = currentScreen,
                    speaker = selectedSpeaker,
                    script = studioScript,
                    onScriptChange = { studioScript = it },
                    onBackHome = { currentScreen = AppScreen.HOME }
                )
            }
        }
    }
}

@Composable
fun HomeDashboardScreen(onNavigate: (AppScreen) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        Text(
            "ESTÚDIO PROFISSIONAL DE LOCUÇÃO E SPOTS",
            color = StudioGold,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )
        // Quatro botões principais em destaque exigidos na especificação
        MainActionHeroButton("🎙️ CRIAR LOCUÇÃO", "Transforme qualquer texto em voz comercial profissional", onClick = { onNavigate(AppScreen.VOICE_STUDIO) })
        MainActionHeroButton("🎧 CRIAR SPOT", "Gerador automático de roteiros e SPOTS publicitários", onClick = { onNavigate(AppScreen.SPOT_GENERATOR) })
        MainActionHeroButton("🗣️ CRIAR MINHA VOZ", "Descreva a voz ideal e ajuste timbre, idade e sotaque", onClick = { onNavigate(AppScreen.CREATE_MY_VOICE) })
        MainActionHeroButton("🎼 ESTÚDIO DE ÁUDIO", "Mixagem com trilha, efeitos, auto-ducking e Editor de Locução", onClick = { onNavigate(AppScreen.AUDIO_STUDIO) })
    }
}

@Composable
fun MainActionHeroButton(title: String, subtitle: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = StudioSurface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(title, color = StudioBrightGold, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(subtitle, color = StudioTextSecondary, fontSize = 13.sp)
        }
    }
}

@Composable
fun SpeakersCatalogScreen(selected: SpeakerProfile, onSelect: (SpeakerProfile) -> Unit) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        item {
            Text("BIBLIOTECA DE LOCUTORES (20 PERFIS REAIS)", color = StudioBrightGold, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
        items(DefaultSpeakersCatalog.allSpeakers) { speaker ->
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = if (speaker.id == selected.id) Color(0xFF2B2512) else StudioSurface
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("${speaker.name} (${speaker.gender.label})", color = StudioBrightGold, fontWeight = FontWeight.Bold)
                        Text(speaker.description, color = StudioTextSecondary, fontSize = 12.sp)
                        Text("Pitch: ${speaker.pitch}x • Velocidade: ${speaker.speed}x • Emoção: ${speaker.defaultEmotion}", color = StudioGold, fontSize = 11.sp)
                    }
                    Button(
                        onClick = { onSelect(speaker) },
                        colors = ButtonDefaults.buttonColors(containerColor = StudioGold)
                    ) {
                        Text("Usar", color = StudioBlack, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun StudioQuickSummaryCard(
    screen: AppScreen,
    speaker: SpeakerProfile,
    script: String,
    onScriptChange: (String) -> Unit,
    onBackHome: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(screen.title, color = StudioBrightGold, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text("Locutor selecionado: ${speaker.name} (${speaker.defaultEmotion})", color = StudioGold, fontSize = 13.sp)
        OutlinedTextField(
            value = script,
            onValueChange = onScriptChange,
            label = { Text("Roteiro de Locução (${script.length} caracteres)") },
            modifier = Modifier.fillMaxWidth().height(160.dp)
        )
        Button(
            onClick = onBackHome,
            colors = ButtonDefaults.buttonColors(containerColor = StudioGold)
        ) {
            Text("Voltar ao Painel Principal", color = StudioBlack, fontWeight = FontWeight.Bold)
        }
    }
}
