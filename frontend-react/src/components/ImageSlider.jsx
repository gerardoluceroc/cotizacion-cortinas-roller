import React, { useState, useEffect } from "react";
import styled from "styled-components";

// Array de imágenes
const images = [
  "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQHH9hyXlX0nu2apl6Ifs6-p7MNEKISCXvkBA&usqp=CAU",
  "https://fernapetcl.vtexassets.com/arquivos/ids/169741-800-auto?v=637677548263930000&width=800&height=auto&aspect=true",
  "https://www.casadesign.cl/wp-content/uploads/2021/10/Roller-Duo.jpg",
  "https://www.servihomes.cl/wp-content/uploads/2022/06/IMG_1698.jpeg",
];

const ImageSliderContainer = styled.div`
  position: relative;
  width: 100%;
  height: 300px; /* establecemos una altura fija */
  margin: 0 auto; /* centramos horizontalmente */
`;

const Image = styled.img`
  width: 100%;
  height: 300px;
  transition: transform 0.5s ease-out; /* transición de 0.3 segundos con aceleración al final */
`;

const Button = styled.button`
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  background-color: transparent;
  border: none;
  cursor: pointer;
  font-size: 2rem;
  color: white;
  z-index: 1;

  &:hover {
    color: #f2f2f2;
  }
`;

const NextButton = styled(Button)`
  right: 0;
  background-color: gray;
`;

const PrevButton = styled(Button)`
  left: 0;
  background-color: gray;
`;

const ImageSlider = () => {
  // Estado para mantener la imagen actual
  const [currentImage, setCurrentImage] = useState(0);

  // Función para avanzar a la siguiente imagen
  const nextImage = () => {
    setCurrentImage((currentImage + 1) % images.length);
  };

  // Función para retroceder a la imagen anterior
  const prevImage = () => {
    setCurrentImage(currentImage === 0 ? images.length - 1 : currentImage - 1);
  };

  // Efecto para cambiar de imagen automáticamente después de 3 segundos
  useEffect(() => {
    const intervalId = setInterval(() => {
      setCurrentImage((currentImage + 1) % images.length);
    }, 3000);

    // Limpiar el intervalo cuando el componente se desmonte
    return () => clearInterval(intervalId);
  }, [currentImage]);

  return (
    <ImageSliderContainer>
      {/* Botón para retroceder */}
      <PrevButton onClick={prevImage}>&#8249;</PrevButton>

      {/* Imagen actual */}
      <Image src={images[currentImage]} alt="Slider Image" />

      {/* Botón para avanzar */}
      <NextButton onClick={nextImage}>&#8250;</NextButton>
    </ImageSliderContainer>
  );
};

export default ImageSlider;
