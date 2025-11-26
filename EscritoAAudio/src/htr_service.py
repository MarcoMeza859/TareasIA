from paddleocr import PaddleOCR
import logging
import os

class HTRService:
    def __init__(self, lang='es'):
        self.logger = logging.getLogger(__name__)
        self.logger.info("Loading PaddleOCR model...")
        # use_angle_cls=True enables orientation classification
        # lang='es' sets the language to Spanish
        # rec_algorithm='SVTR_LCNet' uses the SVTR Transformer model for recognition
        self.ocr = PaddleOCR(use_angle_cls=False, lang=lang)
        self.logger.info("PaddleOCR model loaded successfully.")

    def predict(self, image_path):
        """
        Detects and recognizes text from an image path.
        Returns the concatenated text found in the image.
        """
        if not os.path.exists(image_path):
            raise FileNotFoundError(f"Image not found at {image_path}")

        self.logger.info(f"Processing image: {image_path}")
        result = self.ocr.ocr(image_path)
        self.logger.info(f"PaddleOCR Result: {result}")

        full_text = []
        if result:
            # Check if result is a list of lists (standard) or a list of dicts (some versions)
            res = result[0]
            if isinstance(res, dict):
                # Handle dict structure
                if 'rec_texts' in res:
                    full_text = res['rec_texts']
                elif 'data' in res:
                     # Some versions might use 'data'
                     pass
            elif isinstance(res, list):
                # Handle list structure: [ [box, (text, score)], ... ]
                for line in res:
                    if len(line) >= 2 and isinstance(line[1], (list, tuple)):
                        text = line[1][0]
                        full_text.append(text)
        
        return " ".join(full_text)

if __name__ == "__main__":
    # Simple test
    logging.basicConfig(level=logging.INFO)
    service = HTRService()
    print("HTR Service initialized.")
