import React from 'react';
import styled from 'styled-components';

const FooterContainer = styled.footer`
  display: flex;
  flex-direction: column;
  align-items: center;
  background-color: #222;
  color: white;
  padding: 2rem;
`;

const SocialLinks = styled.ul`
  list-style: none;
  display: flex;
  margin: 0;
  padding: 0;
`;

const SocialLink = styled.a`
  color: white;
  text-decoration: none;
  margin: 0 1rem;
`;

const Footer = () => {
  return (
    <FooterContainer>
      <p>Síguenos en nuestras redes sociales:</p>
      <SocialLinks>
        <li>
          <SocialLink href="https://facebook.com">Facebook</SocialLink>
        </li>
        <li>
          <SocialLink href="https://instagram.com">Instagram</SocialLink>
        </li>
        <li>
          <SocialLink href="https://twitter.com">Twitter</SocialLink>
        </li>
      </SocialLinks>
      <p>Todos los derechos reservados &copy; 2023</p>
    </FooterContainer>
  );
};

export default Footer;
