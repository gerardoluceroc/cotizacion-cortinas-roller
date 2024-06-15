import ClientHome from "../../components/Papelera/ClientHome"
import Footer from "../../components/Footer"
import NavbarResponsive from "../../components/NavbarResponsive";
import NavigationBarLogged from "../../components/Papelera/NavigationBarLogged";

const ClientHomePage = () => {
    return (
        <>
            <NavigationBarLogged />
            <NavbarResponsive/>
            <ClientHome />
            <Footer />
        </>
    )
};

export default ClientHomePage;

