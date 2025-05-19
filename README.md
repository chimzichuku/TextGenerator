# Text Generator: Deterministic, Probabilistic, and Random Word Sequences

A Java-based command-line program that generates word sequences from a given input text using one of three models:
- **Deterministic**: always selects the most frequent next word (with lexicographical tie-breaking)
- **Probabilistic**: returns a ranked list of the most likely next words
- **Random**: generates a word sequence where more frequent next words have higher probability but are selected randomly

## Authors
**Chimzi Chuku** and **Charles Adair**  
April 2025

---

## How It Works

Each generator builds a dictionary by scanning a text file and analyzing word sequences. Once trained, it outputs new sequences starting from a seed word.

### Models Implemented

#### 1. Deterministic Generator
- Always chooses the most frequent word that follows the current word.
- Uses lexicographical order to break frequency ties.

#### 2. Probabilistic Generator
- Returns the top *k* words that most frequently follow a given seed word.
- Results are sorted by frequency, then alphabetically.

#### 3. Random Generator
- Builds a map of potential next words for each word.
- Chooses each next word randomly, with more frequent words having higher chance.

---

## 📁 Project Structure
- book_text_long.txt    <!-- Sample training text file -->
- README.md    <!-- Project documentation -->
- TextGenerator.java    <!-- Main class with entry point and base functionality -->
- DeterministicGenerator.java    <!-- Generator that chooses the most frequent next word -->
- ProbabilisticGenerator.java    <!-- Generator that returns the top-k probable words -->
- RandomGenerator.java    <!-- Generator that randomly picks next word based on frequency -->

---

## ▶️ How to Run
- locate absolute path of source folder containing the files just downloaded. Then run in terminal or command line: cd <absolute path>
- compile the necessary files (always include main TextGenerator file and then the required model)
- run the command line arguments as shown below

```bash
cd /absolute/path/to/downloaded-folder
javac downloadedFolderName//TextGenerator.java downloadedFolderName/SpecifiedModel.java
java downloadedFolderName.TextGenerator <downloadedFolderName.inputFile> <seedWord> <howManyWords> <generatorType>
```

## Example
```bash
cd /Users/chimzichuku/Library/CloudStorage/OneDrive-UniversityofUtah/CS2420/src
javac comprehensive/TextGenerator.java comprehensive/DeterministicGenerator.java
java comprehensive.TextGenerator comprehensive/book_text_long.txt midnight 10 deterministic
```
## Output
```bash
midnight was the only sound that broke the silence of the dark and lonely corridor
```
