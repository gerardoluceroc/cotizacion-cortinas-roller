import React from 'react'
import NavbarResponsive from '../components/NavbarResponsive'
import Footer from '../components/Footer'
import { UsersList } from '../components/AdministratorView/AdministrateUsers/UsersList'

export const UsersListPage = () => {
  return (
    <>
        <NavbarResponsive/>
        <UsersList/>
        <Footer/>
    </>
  )
}
