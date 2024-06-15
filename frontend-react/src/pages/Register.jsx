import NavigationBar from '../components/Papelera/NavigationBar'
import { RegisterForm } from '../components/RegisterForm'
import Footer from '../components/Footer'
import NavbarResponsive from '../components/NavbarResponsive'
import styled from 'styled-components'

const RegisterPageComponent = styled.div`
  min-height: calc(90vh - 80px);

`;

export const RegisterPage = () => {
  return (
    <>
    <RegisterPageComponent>
      <NavbarResponsive/>
      <RegisterForm/>
      
    </RegisterPageComponent>
    <Footer/>
    </>

  )
}
