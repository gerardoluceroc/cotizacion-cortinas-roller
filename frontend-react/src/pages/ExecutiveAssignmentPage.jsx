import ExecutiveAssignment from "../components/ExecutiveView/ExecutiveAssignment";
import Footer from "../components/Footer"
import NavbarResponsive from "../components/NavbarResponsive";
import NavigationBarLogged from "../components/Papelera/NavigationBarLogged";
import RequestManagementUI from "../components/RequestManegementUI";

const ExecutiveAssignmentPage = () => {
    return (
        <>
            <NavbarResponsive/>
            <ExecutiveAssignment />
            <RequestManagementUI/>
            <Footer />
        </>
    )
};

export default ExecutiveAssignmentPage;

