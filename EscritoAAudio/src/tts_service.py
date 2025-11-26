from transformers import VitsModel, AutoTokenizer
import torch
import scipy.io.wavfile as wav
import logging
import os

class TTSService:
    def __init__(self, model_id="facebook/mms-tts-spa"):
        self.logger = logging.getLogger(__name__)
        self.logger.info(f"Loading TTS model: {model_id}...")
        self.tokenizer = AutoTokenizer.from_pretrained(model_id)
        self.model = VitsModel.from_pretrained(model_id)
        self.logger.info("TTS model loaded successfully.")

    def synthesize(self, text, output_path):
        """
        Converts text to audio and saves it to output_path.
        """
        if not text:
            self.logger.warning("No text to synthesize.")
            return

        self.logger.info(f"Synthesizing text: '{text}'")
        inputs = self.tokenizer(text, return_tensors="pt")

        with torch.no_grad():
            output = self.model(**inputs).waveform

        # Save audio
        # MMS-TTS usually outputs at 16kHz, but let's check config if needed. 
        # VitsModel config usually has sampling_rate.
        sampling_rate = self.model.config.sampling_rate
        
        # Output is (batch_size, channels, time), we need (time, channels) or just (time)
        audio_data = output.cpu().numpy().squeeze()
        
        wav.write(output_path, rate=sampling_rate, data=audio_data)
        self.logger.info(f"Audio saved to {output_path}")

if __name__ == "__main__":
    logging.basicConfig(level=logging.INFO)
    tts = TTSService()
    tts.synthesize("Hola, esto es una prueba de síntesis de voz.", "prueba.wav")
