import React from 'react'
import NavigationBar from '../components/Papelera/NavigationBar'
import Footer from '../components/Footer'
import ImageSlider from '../components/ImageSlider'
import { OurProducts } from '../components/OurProducts/OurProducts'

import NavbarResponsive from '../components/NavbarResponsive'

export const HomePage = () => {
  return (
    <>
      <NavbarResponsive/>
      <ImageSlider />
      <OurProducts />
      <Footer />

    </>
  )
}
