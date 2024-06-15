import Footer from "../components/Footer"
import NavigationBarLogged from "../components/Papelera/NavigationBarLogged";
import ClientRequestForm from "../components/ClientView/ClientRequestForm";
import NavbarResponsive from "../components/NavbarResponsive";

const ClientRequestFormPage = () => {
    return (
        <>
            <NavbarResponsive/>
            <ClientRequestForm />
            <Footer />
        </>
    )
};

export default ClientRequestFormPage;

