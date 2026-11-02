# 🎙️ VOZ DO BRASIL — Estúdio Profissional de Vozes Comerciais e SPOTS com IA

> **"Sua voz. Sua criatividade. Sua produção."**

Bem-vindo ao repositório oficial do projeto **VOZ DO BRASIL**, uma solução completa ponta a ponta composta por:
1. **Aplicativo Android Nativo (`VozDoBrasil-Android/`)**: Desenvolvido em **Kotlin**, **Jetpack Compose**, **Material Design 3**, arquitetura **MVVM**, **Room Database (SQLite)**, **Hilt**, **Retrofit/OkHttp** e motor próprio de processamento digital de sinais (**DSP Audio Mixer Engine**) com Auto-Ducking, Equalizador de 3 bandas, Compressor Dinâmico e exportação real em **WAV (PCM 16-bit 44.1kHz)** e **MP3**.
2. **Backend Seguro de IA & TTS (`VozDoBrasil-Backend/`)**: Servidor seguro em **Node.js + Express + SQLite** (com alternativa em **Python FastAPI**) para proteger chaves de API (**Google Gemini**, **ElevenLabs**, **Google Cloud Text-to-Speech**, **OpenAI TTS / Azure Neural**), gerenciar autenticação JWT, controle de consumo/cotas de caracteres e roteamento modular de síntese vocal.
3. **Web App / PWA Instalável (`VozDoBrasil-WebApp/`)**: Versão instantânea que também pode ser instalada diretamente no Android via Chrome ("Adicionar à tela inicial" / WebAPK) ou compilada em APK via Capacitor/TWA.

---

## 📂 Estrutura do Projeto

```text
VozDoBrasil_FullProject/
├── README.md                                  # Documentação completa de arquitetura, APIs e compilação APK
├── VozDoBrasil-Android/                       # Projeto Android Studio completo (Kotlin + Jetpack Compose)
│   ├── settings.gradle.kts
│   ├── build.gradle.kts
│   ├── gradle.properties
│   └── app/
│       ├── build.gradle.kts
│       ├── src/main/
│       │   ├── AndroidManifest.xml            # Permissões de áudio, internet, armazenamento e compartilhamento
│       │   ├── res/                           # Logotipo oficial, ícones adaptativos, temas e strings pt-BR
│       │   └── java/br/com/vozdobrasil/studio/
│       │       ├── VozDoBrasilApp.kt
│       │       ├── MainActivity.kt
│       │       ├── ui/theme/                  # Tema Preto (#09090B), Grafite (#141418) e Dourado (#D4AF37 / #FFD700)
│       │       ├── data/local/                # Room Database (Projetos, Histórico, Vozes Personalizadas, Pastas, Cotas)
│       │       ├── data/remote/               # Integração segura com Backend Proxy + Fallback Nativo Android TTS
│       │       ├── domain/model/              # 20 Perfis de Locutores Reais, Estilos de SPOT, Configurações DSP
│       │       ├── audio/                     # Motor de Mixagem Multifaixa, Auto-Ducking, EQ, Compressor e Exportador WAV
│       │       ├── viewmodel/                 # StudioViewModel (MVVM completo e reativo com StateFlow)
│       │       └── ui/screens/                # Todas as 14 telas do aplicativo em Jetpack Compose
└── VozDoBrasil-Backend/                       # Backend Seguro para proteção de chaves de API e controle de consumo
    ├── package.json
    ├── .env.example                           # Modelo de variáveis de ambiente (GEMINI_API_KEY, ELEVENLABS_API_KEY, etc.)
    ├── server.js                              # Servidor Express com Proxy Seguro, Rate Limit e Controle de Cotas SQLite
    └── schema.sql                             # Esquema do banco de dados de usuários, produções e auditoria de consumo
```

---

## 🚀 Como Configurar o Backend Seguro (Proteção das Chaves de API)

As chaves de API **nunca** ficam expostas no APK do cliente. Todo o tráfego de geração de roteiros (Google Gemini) e síntese neural premium (ElevenLabs / Google Cloud TTS) passa pelo backend seguro.

### Passo a Passo:
1. Acesse a pasta `VozDoBrasil-Backend/`:
   ```bash
   cd VozDoBrasil-Backend
   npm install
   cp .env.example .env
   ```
2. Edite o arquivo `.env` com suas chaves reais:
   ```env
   PORT=8080
   JWT_SECRET=sua_chave_secreta_super_segura_aqui
   GEMINI_API_KEY=AIzaSySuaChaveDoGoogleGeminiAqui
   ELEVENLABS_API_KEY=sk_SuaChaveDaElevenLabsAqui
   GOOGLE_APPLICATION_CREDENTIALS=./google-tts-credentials.json
   ```
3. Inicie o servidor:
   ```bash
   npm start
   ```
4. No aplicativo Android, acesse a tela **Gerenciamento de API** e informe a URL do seu Backend (ex.: `https://api.seudominio.com.br` ou `http://10.0.2.2:8080` no emulador Android).
   - **Importante**: Caso nenhuma API externa esteja configurada, o aplicativo informa claramente o status de cada provedor e utiliza automaticamente o motor **Android Native TextToSpeech (`pt-BR`) combinado com nosso processador DSP de formantes, graves e compressão de estúdio**, garantindo que você possa gerar, mixar e exportar áudios reais imediatamente sem simulações falsas!

---

## 📱 Como Gerar o Arquivo APK Instalável no Android Studio

1. Abra o **Android Studio Ladybug (2024.2.1) ou superior**.
2. Selecione **Open** e escolha a pasta `VozDoBrasil-Android`.
3. Aguarde a sincronização do Gradle (`Gradle Sync`).
4. Para compilar o APK de instalação direta pelo terminal:
   ```bash
   cd VozDoBrasil-Android
   ./gradlew assembleDebug
   ```
   Ou pelo menu do Android Studio: **Build > Build Bundle(s) / APK(s) > Build APK(s)**.
5. O arquivo instalável estará disponível em:
   `VozDoBrasil-Android/app/build/outputs/apk/debug/app-debug.apk`
6. Transfira para qualquer celular Android (Android 8.0 / API 26 ou superior) e instale!
