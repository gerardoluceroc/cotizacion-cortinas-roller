import React from 'react'
import { ProductItem } from './ProductItem'
import styled from 'styled-components'

const StyledDiv = styled.div`
  background-color:  #f0f3f4;
  padding: 10px;



`;

const ListadoProductos = styled.div`
  display: flex; // Aplicamos el estilo display:flex para que los elementos hijos se muestren en una fila horizontal
  flex-direction: row; // Aplicamos el estilo flex-direction:row para indicar que los elementos hijos deben distribuirse en una fila horizontal
  flex-wrap: wrap; // Aplicamos el estilo flex-wrap:wrap para que los elementos hijos se distribuyan en varias filas si no caben todos en una sola
  justify-content: center; // Aplicamos el estilo justify-content:center para centrar los elementos hijos horizontalmente dentro del contenedor padre
  align-items: flex-start; // Aplicamos el estilo align-items:flex-start para alinear los elementos hijos en la parte superior del contenedor padre
  gap: 20px; // Aplicamos el estilo gap:20px para agregar un espacio de 20px entre cada elemento hijo
  margin-top: 20px; // Aplicamos el estilo margin-top:20px para agregar un espacio de 20px en la parte superior del contenedor padre
`;

const Titulo = styled.h1`
  font-size: xx-large;
`;

export const OurProducts = () => {

  let productos = [
    {
      id: 1,
      titulo: "Roller Blackout",
      imagen: "https://aridesign.cl/wp-content/uploads/2021/08/5Berlingris1_8c523c0e-d2ca-4804-82b5-9cc6ded083fa_1200x.jpg",
      descripcion: "Bloquea completamente la luz y protege tu privacidad con nuestras Cortinas Roller Blackout de alta calidad. Disponible en una variedad de tamaños y colores para adaptarse a tus necesidades"
    },
    {
      id: 2,
      titulo: "Roller Dúo",
      imagen: "https://homeseven.cl/wp-content/uploads/2021/03/ca1c1a368db2beba448a28a09ac69cf.jpg",
      descripcion: "La cortina roller duo combina dos telas en una sola, permitiéndote controlar la entrada de luz y privacidad. Con su diseño elegante y minimalista, es una opción versátil y práctica para cualquier habitación."
    },
    {
      id: 3,
      titulo: "Roller Screen",
      imagen: "https://aluprof.cl/wp-content/uploads/2015/05/Cortinas-roller-.jpg",
      descripcion: "Descubre la elegancia y funcionalidad de nuestras cortinas roller screen. Con tejidos de alta calidad y un diseño moderno, estas cortinas ofrecen el equilibrio perfecto entre privacidad y luminosidad. Transforma tus espacios y dale a tus ventanas ese toque de distinción que se merecen."
    },
    {
      id: 4,
      titulo: "Roller Dúo Transparente",
      imagen: "https://static2.elcontainer.cl/8515-big_default/cortina-roller-duo-blanca.jpg",
      descripcion: "Con su diseño innovador, estas cortinas ofrecen una combinación única de privacidad y luminosidad. La doble capa de tejido permite controlar la entrada de luz y mantener una conexión visual con el exterior. Transforma tus espacios con un toque moderno y sofisticado."
    }
  ];

  //Funcion que genera los componentes ProductItem con la info de los productos disponibles de acuerdo al listado de entrada
  const mostrarProductos = (productos) => {
    return productos.map((producto) => (
      <ProductItem
        key={producto.id}
        title={producto.titulo}
        image={producto.imagen}
        description={producto.descripcion}
      />
    ));
  };

  
  return (
    <>
    <StyledDiv>
      <Titulo>Nuestros Productos</Titulo>
      <ListadoProductos>{mostrarProductos(productos)}</ListadoProductos>
    </StyledDiv>
    </>
  )
}
