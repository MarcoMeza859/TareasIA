from PIL import Image, ImageDraw, ImageFont
import os

def create_sample_image(text, output_path):
    # Create a white image
    img = Image.new('RGB', (2000, 200), color='white')
    d = ImageDraw.Draw(img)
    
    # Use a default font, size 40
    # On Windows, arial.ttf is usually available.
    try:
        font = ImageFont.truetype("arial.ttf", 40)
    except IOError:
        font = ImageFont.load_default()

    # Draw text in black
    d.text((50, 75), text, fill=(0, 0, 0), font=font)
    
    img.save(output_path)
    print(f"Sample image saved to {output_path}")

if __name__ == "__main__":
    if not os.path.exists("data"):
        os.makedirs("data")
    create_sample_image("Hola gente, este es un ejemplo de como se transcribe y convierte en audio un texto", "data/test_image.png")
