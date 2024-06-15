import React from 'react';
import ReactDOM from 'react-dom';
import CustomAlert from '../components/CustomAlert';

export const showAlert = (message) => {
  const notificationRoot = document.createElement('div');
  document.body.appendChild(notificationRoot);

  const closeNotification = () => {
    ReactDOM.unmountComponentAtNode(notificationRoot);
    document.body.removeChild(notificationRoot);
  };

  ReactDOM.render(
    <CustomAlert message={message} onClose={closeNotification} />,
    notificationRoot
  );
};

