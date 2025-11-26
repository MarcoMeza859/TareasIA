import argparse
import logging
import os
from src.htr_service import HTRService
from src.text_processor import TextProcessor
from src.tts_service import TTSService

def main():
    parser = argparse.ArgumentParser(description="Handwritten Text to Speech Pipeline")
    parser.add_argument("--image", required=True, help="Path to the input image with handwritten text")
    parser.add_argument("--output", default="output.wav", help="Path to the output audio file")
    args = parser.parse_args()

    # Setup logging
    logging.basicConfig(level=logging.INFO, format='%(asctime)s - %(levelname)s - %(message)s')
    logger = logging.getLogger(__name__)

    try:
        # 1. HTR
        logger.info("Step 1: Initializing HTR Service...")
        htr = HTRService()
        logger.info("Recognizing text from image...")
        raw_text = htr.predict(args.image)
        logger.info(f"Raw Text: {raw_text}")

        # 2. Text Processing
        logger.info("Step 2: Processing text...")
        processor = TextProcessor()
        clean_text = processor.clean_text(raw_text)
        logger.info(f"Clean Text: {clean_text}")

        if not clean_text:
            logger.error("No text detected. Aborting.")
            return

        # 3. TTS
        logger.info("Step 3: Synthesizing audio...")
        tts = TTSService()
        tts.synthesize(clean_text, args.output)
        logger.info(f"Pipeline completed successfully. Audio saved to {args.output}")

    except Exception as e:
        logger.error(f"An error occurred: {e}")
        import traceback
        traceback.print_exc()

if __name__ == "__main__":
    main()
