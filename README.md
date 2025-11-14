# Library App - Mobile Developer Assessment

A simple Android library app demonstrating clean architecture and modern Android development practices.

## Task Requirements

**Goal**: Create a mobile library app with a Book List ViewModel following best practices.

**Requirements Met**:
- Android app in Kotlin
- Mocked data from Google Books API format (`volumes.json`)
- Book List ViewModel with dynamic display
- Search UI (functional implementation)
- Filter/Sort UI options (visual placeholders)
- Clean, structured code
- Focus on architecture over full functionality

## Setup & Run

### Prerequisites
- Android Studio (2023.1.1+)
- JDK 11+
- Android SDK API 24+

### Run Instructions
1. Open project in Android Studio
2. Sync Gradle (automatic)
3. Click Run
4. Select emulator/device

Displays 10 books with working search

## Key Features

## MVVM Pattern
```
UI (Composables) → ViewModel (StateFlow) → Repository → JSON Data
```

### Implemented 
- **Book List**: Displays 10 books from mocked data
- **Search**: Real-time filtering by title/author
- **Book Details**: Full information screen with navigation
- **Loading States**: Spinner while data loads
- **Error Handling**: Displays errors if data fails

### Visual Placeholders 
- **Filter Dropdown**: Shows options (All, Fiction, Non-Fiction, Art, Science)
- **Sort Dropdown**: Shows options (Title A-Z, Author, Rating, Date)
- *Note: These update UI state but don't modify the list per requirements*

## Technical Decisions

### Why Jetpack Compose?
- Modern Android standard (Google recommended)
- Less boilerplate (~50% less code than XML)
- Declarative UI (easier to maintain)
- Better for demonstrating current skills

### Why Separate State/Screen/ViewModel?
- Single Responsibility: Each file has one purpose
- Testability: Test logic and UI independently
- Maintainability: Easier to navigate and update
- Industry Standard: Follows Google architecture guidelines

### Why StateFlow over LiveData?
- Kotlin-first (better integration)
- Cleaner Compose syntax
- Modern reactive approach

## Architecture Highlights

- **State Management**:
  - ViewModel exposes immutable state (`val uiState: StateFlow<BookListUiState>`)
  - UI observes and reacts (`val uiState by viewModel.uiState.collectAsState()`)

- **Repository Pattern**:
  - Abstracts data source (`class BookRepository { suspend fun getBooks(): Result<List<Book>> }`)
  - Easy to swap JSON → API later

- **Navigation**:
  - Type-safe navigation with Compose 

## What This Demonstrates

**Mobile Best Practices**:
- Clean Architecture (separation of concerns)
- MVVM Pattern (state management)
- Reactive Programming (StateFlow)
- Kotlin Coroutines (async operations)
- Jetpack Compose (modern UI)
- Material Design 3 (modern UX)
- Navigation Component (screen transitions)
- Code organization (logical structure)

**Focus Areas** (per requirements):
- Layout & UI structure
- State management
- Component architecture
- Code quality & documentation

## Testing the App

1. **Launch**: See list of books
2. **Search**: Type "Tom Gauld" → filters to 1 book
3. **Click book**: Opens details screen
4. **Back button**: Returns to list (search preserved)
5. **Filter/Sort**: Click dropdowns to see options

## Future Enhancements

- [ ] Add book cover images (Coil library)
- [ ] Add favorites/bookmarks
- [ ] Connect to real Google Books API
- [ ] Add offline support (Room database)
- [ ] Add unit & UI tests

---

**Created for**: Mobile Developer Assessment  
**Focus**: Demonstrating Android architecture and modern development practices
