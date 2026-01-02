# VoiceNote Android - Project Context

## Project Overview

VoiceNote is a combined voice memo and note-taking Android application built in Kotlin with Jetpack Compose. The core concept is allowing users to attach voice recordings to text notes, creating a unified system for both audio and text-based note-taking.

**Status**: Work in progress
**Author**: Oskar Larsson (all design and implementation)
**Platform**: Android (Kotlin)

## Core Features

1. **Text Notes**: Create and edit notes with title and text content
2. **Voice Recordings**: Record audio memos using the device microphone
3. **Attachment System**: Link recordings to specific notes or keep them standalone
4. **Dual View**: Toggle between viewing all notes or all recordings
5. **Audio Playback**: Play recordings with Media3 ExoPlayer, supports background playback
6. **Recording Management**: Rename, delete, and organize recordings

## Architecture

### Multi-Module Structure

```
VoiceNote_android/
├── app/              # Main application module (features, UI, data, ViewModels)
├── audioplayer/      # Audio playback library (Media3 ExoPlayer wrapper)
└── domain/           # Domain logic module (currently empty, prepared for future use)
```

### Architecture Pattern: MVVM + Repository

```
UI (Compose) → ViewModel → Repository → DAO → Room Database
                    ↓
              AudioPlayer/Recorder (Utils)
```

**Key Patterns**:
- MVVM with reactive Kotlin Flows
- Repository pattern for data abstraction
- Dependency Injection with Koin
- Event-driven UI with sealed interfaces
- Stateless composables with state hoisting
- Type-safe navigation with kotlinx.serialization

## Technology Stack

| Layer | Technologies |
|-------|-------------|
| **UI** | Jetpack Compose, Material3, Lottie, ConstraintLayout Compose |
| **Architecture** | MVVM, Repository Pattern, Clean Architecture principles |
| **Database** | Room (version 9 with destructive migration) |
| **DI** | Koin |
| **Navigation** | AndroidX Navigation3 (type-safe with kotlinx.serialization) |
| **Audio Recording** | MediaRecorder API |
| **Audio Playback** | Media3 ExoPlayer with MediaSession |
| **Async** | Kotlin Coroutines and Flows |
| **Fonts** | Google Fonts (Space Grotesk) |
| **Build** | Gradle Kotlin DSL |

## Data Layer

### Database Entities

**NoteEntity** (`data/entity/NoteEntity.kt`):
```kotlin
noteId: String (PK, UUID)
noteTitle: String
noteTxtContent: String
date: String
```

**RecordingEntity** (`data/entity/RecordingEntity.kt`):
```kotlin
recordingId: String (PK, UUID)
recordingNumber: Int (auto-incremented for naming)
recordingFileName: String
recordingTitle: String?
recordingLink: String (file path)
recordingDate: String
recordingDuration: String
noteId: String? (FK to NoteEntity, SET_NULL on delete)
```

**Relationship**: Recordings optionally belong to a note. When a note is deleted, recordings are preserved but the noteId is set to null.

### Repositories

- **NotesRepository**: CRUD operations for notes, entity-to-model conversion
- **RecordingsRepository**: Recording management, handles both DB and file system operations

### Audio Recording Details

- **Format**: .m4a (AAC encoding)
- **Bitrate**: 128kbps
- **Sample Rate**: 44.1kHz
- **Storage**: App's external files directory (`Environment.DIRECTORY_RECORDINGS`)
- **Naming**: Auto-numbered (e.g., "Recording 1", "Recording 2")
- **Features**: Pause/resume support, automatic duration extraction

### Audio Playback Details

- **Service**: `PlaybackService` (foreground MediaSessionService)
- **Player**: Media3 ExoPlayer via MediaController
- **Background Playback**: Supported with media notifications
- **Position Updates**: Every 200ms during playback
- **States**: Idle, Ready, Playing, Buffering, Ended, Error

## Navigation Structure

### Screens

**Type-safe navigation using sealed classes**:

```kotlin
sealed interface Screen {
    object Home
    data class EditNote(noteId: String)
}

sealed interface HomeNavigation {
    object NotesList
    object RecordingsList
}
```

### Screen Details

**HomeScreen** (`features/HomeScreen.kt`):
- TopToggleBar switches between NotesList and RecordingsList
- Bottom actions: "New note" button, "Record" button
- Recording bottom sheet modal

**EditNoteScreen** (`features/EditNoteScreen.kt`):
- Editable title and content fields with real-time updates
- View recordings attached to this note
- Bottom actions: "Recordings" menu, "Record" button
- More menu (three dots) for deletion
- Auto-saves on exit if modified

## Dependency Injection (Koin)

**Initialization**: `VoiceNoteApplication.kt` (Application.onCreate)

**Modules** (`di/Modules.kt`):
```kotlin
viewModel        // All ViewModels
recorder         // Recorder singleton
audioPlayerModule // LocalAudioPlayer singleton
repositoryModule // NotesRepository, RecordingsRepository (singletons)
daoModule        // NoteDao, RecordingDao (singletons)
dataModule       // (empty, reserved)
utils            // (empty, reserved)
```

## Key UI Components

**Stateless Components** (state hoisting pattern):
- `NotesList` / `NoteItem` - Note display with recording indicators
- `RecordingsList` / `RecordingMenuItem` - Expandable audio player UI
- `RecordingBottomSheet` - Recording modal with Lottie animation
- `TopToggleBar` - View switcher
- `BottomBox` - Variant-based action bar
- `NoteScreenStateless` (NoteView) - Editable note view
- `TopAppBarCustom` - Custom app bar with navigation
- `MoreCircleMenu` - Dropdown menu
- `LinearProgressBar` - Audio progress indicator

**UI Architecture**:
- Material3 theming with custom color schemes
- Dark/light theme support
- Custom Space Grotesk font family
- `UiAudioPlayerClickListener` interface for event handling

## Permissions

Required permissions (AndroidManifest):
- `RECORD_AUDIO` - Voice recording
- `READ_EXTERNAL_STORAGE` - File access
- `FOREGROUND_SERVICE` - Background audio
- `FOREGROUND_SERVICE_MEDIA_PLAYBACK` - Media playback service

## Important File Locations

### Core Features
- `app/src/main/java/com/larsson/voicenote_android/features/HomeScreen.kt`
- `app/src/main/java/com/larsson/voicenote_android/features/EditNoteScreen.kt`

### ViewModels
- `app/src/main/java/com/larsson/voicenote_android/viewmodels/HomeViewModel.kt`
- `app/src/main/java/com/larsson/voicenote_android/viewmodels/NoteViewModel.kt`
- `app/src/main/java/com/larsson/voicenote_android/viewmodels/RecordingViewModel.kt`
- `app/src/main/java/com/larsson/voicenote_android/viewmodels/AudioPlayerViewModel.kt`

### Data Layer
- `app/src/main/java/com/larsson/voicenote_android/data/room/NoteDatabase.kt`
- `app/src/main/java/com/larsson/voicenote_android/data/entity/NoteEntity.kt`
- `app/src/main/java/com/larsson/voicenote_android/data/entity/RecordingEntity.kt`
- `app/src/main/java/com/larsson/voicenote_android/data/repository/NotesRepository.kt`
- `app/src/main/java/com/larsson/voicenote_android/data/repository/RecordingsRepository.kt`

### Audio
- `app/src/main/java/com/larsson/voicenote_android/features/audiorecorder/Recorder.kt`
- `audioplayer/src/main/java/com/larsson/voicenote_android/audioplayer/LocalAudioPlayer.kt`
- `audioplayer/src/main/java/com/larsson/voicenote_android/audioplayer/PlaybackService.kt`

### DI & Navigation
- `app/src/main/java/com/larsson/voicenote_android/di/Modules.kt`
- `app/src/main/java/com/larsson/voicenote_android/navigation/VNNavHost.kt`
- `app/src/main/java/com/larsson/voicenote_android/navigation/Screen.kt`

## Recent Changes

Based on git status, recent work includes:
- Split `NotesViewModel` into `HomeViewModel` and `NoteViewModel`
- Updated navigation structure in `VNNavHost.kt`
- Modified repository implementations
- Updated DI modules
- Refactored home and edit screens
- Updated UI components (NoteScreenStateless, NotesList)

## Development Notes

### Code Style
- Kotlin idiomatic code
- Jetpack Compose declarative UI
- Reactive data flows with StateFlows
- Event-driven architecture for user interactions
- Stateless composables with state hoisting

### Testing
- Domain module has example test files
- Main app module testing setup pending

### TODOs Found in Code
- Permission handling improvements
- RecordingEvent interface (currently empty)
- Potential ConstraintLayout removal in EditNoteScreen
- Share functionality implementation
- Error handling for file operations

### Database Migrations
- Currently using `fallbackToDestructiveMigration()`
- Consider proper migration strategy before production release

## Design Philosophy

- **Custom Design**: Everything designed by the author in Figma
- **Material3**: Using Material Design 3 principles
- **Minimalist**: Clean, focused interface
- **Reactive**: Real-time updates across the app
- **User-centric**: Simple, intuitive interaction patterns

## Common Development Tasks

### Adding a new screen
1. Create screen composable in `features/`
2. Add sealed class entry to `navigation/Screen.kt`
3. Update `VNNavHost.kt` with new route
4. Create ViewModel if needed and register in Koin

### Adding a new database field
1. Update entity in `data/entity/`
2. Update DAO queries in `data/room/`
3. Update repository mappings
4. Increment database version or handle migration
5. Update UI/ViewModel to use new field

### Adding a new feature
1. Plan data model changes (entities, DAOs)
2. Create/update repositories
3. Create ViewModel with state management
4. Build UI composables
5. Wire up navigation
6. Register dependencies in Koin modules

## Git Branch Strategy

- **main**: Main stable branch
- **develop**: Current development branch (active)
- Feature branches merged via PRs

---

**Last Updated**: 2025-12-26
**Database Version**: 9
**Min SDK**: Check build.gradle.kts
**Target SDK**: Check build.gradle.kts