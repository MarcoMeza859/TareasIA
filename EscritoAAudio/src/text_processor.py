import re

class TextProcessor:
    def __init__(self):
        pass

    def clean_text(self, text):
        """
        Cleans and normalizes text for TTS.
        """
        if not text:
            return ""

        # Remove extra whitespace
        text = re.sub(r'\s+', ' ', text).strip()

        # Basic corrections (can be expanded)
        # Example: correct common OCR errors if found
        
        # Ensure punctuation is reasonable for TTS
        # If text doesn't end with punctuation, add a period.
        if text and text[-1] not in '.!?':
            text += '.'

        return text

if __name__ == "__main__":
    processor = TextProcessor()
    sample = "  Hola   mundo.  Esto es una prueba "
    print(f"Original: '{sample}'")
    print(f"Cleaned: '{processor.clean_text(sample)}'")
