import { Call, CardTravel, MailLock, MailOutline, MessageOutlined, MessageRounded } from '@mui/icons-material'
import { Box, Container, Stack} from '@mui/material'
import React from 'react'

export const Header = () => {
  return (

    <Box sx={{
        bgcolor: '#ff5000',
        maxWidth: 'xl',
        color: 'white',
        display: 'flex',
        justifyContent: 'center',
        lignItems: 'center',

    }} >
        <Stack
         direction={'row'}
         spacing={'20px'}
         >
            <Stack
            direction={'row'}
            spacing={'5px'}>

                <Call/>
                <h4>+56 9 7777 6666</h4>
                

            </Stack>

            <Stack
            direction={'row'}
            spacing={'5px'}>

                <MailOutline/>
                <h4>contacto@mail.com</h4>

            </Stack>

        
        </Stack>


    </Box>
  )
}