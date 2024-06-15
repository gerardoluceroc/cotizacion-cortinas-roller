import SellerHome from "../../components/Papelera/SellerHome";
import Footer from "../../components/Footer"
import NavigationBarLogged from "../../components/Papelera/NavigationBarLogged";
import NavbarResponsive from "../../components/NavbarResponsive";

const SellerHomePage = () => {
    return (
        <>
            <NavigationBarLogged />
            <NavbarResponsive/>
            <SellerHome />
            <Footer />
        </>
    )
};

export default SellerHomePage;

