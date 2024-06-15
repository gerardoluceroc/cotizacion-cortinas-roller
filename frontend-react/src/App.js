import { Route, Routes } from "react-router";
import { HomePage } from "./pages/HomePage";
import { ClientRequestPage } from "./pages/ClientRequestPage";
import {RegisterPage } from "./pages/Register";
//import LoginPage from "./Pages/LoginPage";
import LoginPage from "./pages/LoginPage";
import ClientHomePage from "./pages/Papelera/ClientHomePage";
import ClientRequestFormPage from "./pages/ClientRequestFormPage";
import SellerLoginFormPage from "./pages/Papelera/SellerLoginFormPage";
import SellerHomePage from "./pages/Papelera/SellerHomePage";
import SellerMyRequestsPage from "./pages/SellerMyRequestsPage";
import ExecutiveLoginFormPage from "./pages/Papelera/ExecutiveLoginFormPage";
import ExecutiveHomePage from "./pages/Papelera/ExecutiveHomePage";
import ExecutiveAssignmentPage from "./pages/ExecutiveAssignmentPage";
import SellerInformationPage from "./pages/SellerInformationPage";
import AdminLoginFormPage from "./pages/AdminLoginFormPage";
import AdminHomePage from "./pages/AdminHomePage";
import AdminReadCoveragesPage from "./pages/AdminReadCoveragesPage";
import AdminReadCurtainsPage from "./pages/AdminReadCurtainsPage";
import AdminReadCurtainPipesPage from "./pages/AdminReadCurtainPipesPage";
import AdminReadPermissionsPage from "./pages/AdminReadPermissionsPage";
import AdminReadRequestsPage from "./pages/AdminReadRequestsPage";
import AdminReadRolesPage from "./pages/AdminReadRolesPage";
import AdminReadStatusPage from "./pages/AdminReadStatusPage";
import AdminReadUsersPage from "./pages/Papelera/AdminReadUsersPage";
import UpdateClientInfoPage from "./pages/UpdateClientInfoPage";
import { UsersListPage } from "./pages/UsersListPage";
import SellerQuotePage from "./pages/SellerQuotePage";
import AdminReadProfitMarginPage from "./pages/AdminReadProfitMarginPage";
import RequestManagementPage from "./pages/RequestManagementPage";
import SellerMyQuotesPage from "./pages/SellerMyQuotesPage";
import AdminReadIVAPage from "./pages/AdminReadIVAPage";


function App() {
  return (

    <>

    <Routes>
      <Route path="/" element={<HomePage/>} />
      <Route path="/client/request" element={<ClientRequestPage/>}/>
      <Route path="/register" element={<RegisterPage/>} />
      <Route path="/client/updateInfo" element={<UpdateClientInfoPage/>} />

      <Route path="/loginAdmin" element={<AdminLoginFormPage />} />
      <Route path="/admin" element={<AdminHomePage />} />
      <Route path="/admin/readCoverages" element={<AdminReadCoveragesPage />} />
      <Route path="/admin/readProfitMargins" element={<AdminReadProfitMarginPage />} />
      <Route path="/admin/readCurtains" element={<AdminReadCurtainsPage />} />
      <Route path="/admin/readCurtainPipes" element={<AdminReadCurtainPipesPage />} />
      <Route path="/admin/readPermissions" element={<AdminReadPermissionsPage />} />
      <Route path="/admin/readRequests" element={<AdminReadRequestsPage />} />
      <Route path="/admin/readRoles" element={<AdminReadRolesPage />} />
      <Route path="/admin/readStatus" element={<AdminReadStatusPage />} />
      <Route path="/admin/readUsers" element={<UsersListPage/>} />
      <Route path="/admin/requestManagement" element={<RequestManagementPage />} />
      <Route path= "/admin/quotes" element={<SellerMyQuotesPage/>}/>
      <Route path="/admin/readIVA" element={<AdminReadIVAPage />} />

      <Route path="/executive/executiveAssignment" element={<ExecutiveAssignmentPage />} />
      <Route path="/executive/requestManagement" element={<RequestManagementPage />} />
      <Route path="/executive" element={<ExecutiveHomePage />} />
      <Route path="/loginExecutive" element={<ExecutiveLoginFormPage />} />

      <Route path="/seller/sellerInformation" element={<SellerInformationPage />} />
      <Route path="/seller/assignnedRequest" element={<SellerMyRequestsPage />} />
      <Route path="/loginSeller" element={<SellerLoginFormPage/>} />
      <Route path="/seller" element={<SellerHomePage />} />
      <Route path="/seller/quote" element={<SellerQuotePage />} />
      <Route path="/seller/myQuotes" element={<SellerMyQuotesPage/>} />

      <Route path="/request" element={<ClientRequestFormPage />} />
      {/*<Route path="/client" element={<ClientHomePage/>} />*/}
      <Route path="/login" element={<LoginPage/>} />
      
    </Routes>


  
    
    </>

  );
}

export default App;
