import Footer from "../components/Footer"
import NavbarResponsive from "../components/NavbarResponsive";
import { UpdateClientInfo } from "../components/ClientView/UpdateClientInfo";
import styled from "styled-components";


const Div = styled.div`
    min-height: calc(85vh - 80px);
`;
const UpdateClientInfoPage = () => {
    return (
        <Div>
            <NavbarResponsive/>
            <Div>
            <UpdateClientInfo />
            </Div>
            <Footer />
        </Div>
    )
};

export default UpdateClientInfoPage;

