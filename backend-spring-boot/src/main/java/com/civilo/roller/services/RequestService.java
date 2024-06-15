package com.civilo.roller.services;

import com.civilo.roller.Entities.RequestEntity;
import com.civilo.roller.Entities.SellerEntity;
import com.civilo.roller.Entities.CoverageEntity;
import com.civilo.roller.Entities.CurtainEntity;
import com.civilo.roller.Entities.StatusEntity;
import com.civilo.roller.Entities.UserEntity;
import com.civilo.roller.repositories.RequestRepository;
import com.civilo.roller.repositories.SellerRepository;
import com.civilo.roller.repositories.CoverageRepository;
import com.civilo.roller.repositories.CurtainRepository;
import com.civilo.roller.repositories.StatusRepository;
import com.civilo.roller.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.civilo.roller.exceptions.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class RequestService {
    @Autowired
    RequestRepository requestRepository;

    @Autowired
    SellerService sellerService;

    @Autowired
    StatusService statusService;

    @Autowired 
    SellerRepository sellerRepository;

    @Autowired 
    CoverageRepository coverageRepository;

    @Autowired 
    CurtainRepository curtainRepository;

    @Autowired 
    StatusRepository statusRepository;

    @Autowired 
    UserRepository userRepository;
 
    // Get all
    // El siguiente método retorna un listado el cual contiene TODA la información asociada a las solicitudes
    public List<RequestEntity> getRequests(){
        return (List<RequestEntity>) requestRepository.findAll();
    }

    //Get By CLient ID
    //Permite obtener las solicitudes de un cliente especifico
    public ArrayList<RequestEntity> getRequestByUserId(Long id_cliente){
        //List<RequestEntity> listado =  (List<RequestEntity>) requestRepository.findRequestByUserId(id_cliente);
        //System.out.println("HOLAAAAAAAAAAAAAAAAAA"+listado);
        //System.out.println("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");

        return requestRepository.findRequestByUserId(id_cliente);
    }

    // Get by id
    // Permite obtener la informacion de una solicitud en especifico.
    public Optional<RequestEntity> getRequestById(Long id){
        return requestRepository.findById(id);
    }

    // Create
    // Permite guardar un objeto del tipo "RequestEntity" en la base de datos.
    public RequestEntity createRequest(RequestEntity request){   
        return requestRepository.save(request);  
    }

    // Update
    // Permite actualizar los datos de un objeto del tipo "RequestEntity" en la base de datos.
    public RequestEntity updateRequest(Long requestID, RequestEntity request){

        RequestEntity existingRequest = requestRepository.findById(requestID)
            .orElseThrow(() -> new EntityNotFoundException("Solicitud no encontrada con el ID: " + requestID));

        existingRequest.setAdmissionDate(request.getAdmissionDate());
        existingRequest.setClosingDate(request.getClosingDate());
        existingRequest.setDeadline(request.getDeadline());
        existingRequest.setDescription(request.getDescription());
        existingRequest.setReason(request.getReason());
        existingRequest.setSellerId(request.getSellerId());

        CoverageEntity existingCoverage = coverageRepository.findById(request.getCoverage().getCoverageID())
            .orElseThrow(() -> new EntityNotFoundException("Cobertura no encontrada con el ID: " + request.getCoverage().getCoverageID()));
        Long newCoverageId = request.getCoverage().getCoverageID();
        CoverageEntity newCoverage = coverageRepository.findById(newCoverageId)
            .orElseThrow(() -> new EntityNotFoundException("Cobertura no encontrada con el ID: " + newCoverageId));
        existingRequest.setCoverage(newCoverage);
        
        CurtainEntity existingCurtain = curtainRepository.findById(request.getCurtain().getCurtainID())
            .orElseThrow(() -> new EntityNotFoundException("Cortina no encontrada con el ID: " + request.getCurtain().getCurtainID()));
        Long newCurtainId = request.getCurtain().getCurtainID();
        CurtainEntity newCurtain = curtainRepository.findById(newCurtainId)
            .orElseThrow(() -> new EntityNotFoundException("Cortina no encontrada con el ID: " + newCurtainId));
        existingRequest.setCurtain(newCurtain);

        StatusEntity existingStatus = statusRepository.findById(request.getStatus().getStatusID())
            .orElseThrow(() -> new EntityNotFoundException("Status no encontrado con el ID: " + request.getStatus().getStatusID()));
        Long newStatusId = request.getStatus().getStatusID();
        StatusEntity newStatus = statusRepository.findById(newStatusId)
            .orElseThrow(() -> new EntityNotFoundException("Status no encontrado con el ID: " + newStatusId));
        existingRequest.setStatus(newStatus);

        UserEntity existingUser = userRepository.findById(request.getUser().getUserID())
            .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con el ID: " + request.getUser().getUserID()));
        Long newUserId = request.getUser().getUserID();
        UserEntity newUser = userRepository.findById(newUserId)
            .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con el ID: " + newUserId));
        existingRequest.setUser(newUser);
        
        RequestEntity updatedRequest = requestRepository.save(existingRequest);
        return updatedRequest;
    }

    // Delete all
    // Permite eliminar todas las solicitudes de un sistema.
    public void deleteRequest() {
        requestRepository.deleteAll();
    }

    // Delete by id
    // Permite eliminar una solicitud en especifico del sistema.
    public void deleteRequestById(Long id){
        requestRepository.deleteById(id);
    }
    
    // Permite verificar si existe una solicitud en el sistema, según el id ingresado.
    public boolean existsRequestById(Long id){
        return requestRepository.findById(id).isPresent();
    }


    //----------------------------------------------------------------------------------------------

    public RequestEntity saveRequest(RequestEntity request){
        return requestRepository.save(request);
    }

    public List<RequestEntity> getRequestBySellerId(Long sellerId) {
        List<RequestEntity> requests = getRequests();
        List<RequestEntity> myRequest = new ArrayList<>();
        for (int i = 0; i < requests.size(); i++){
            if (requests.get(i).getSellerId() == sellerId){
                myRequest.add(requests.get(i));
            }
        }
        return myRequest;
    }

    public void automaticAssignment(){
        List<RequestEntity> requestEntities = getRequests();
        List<SellerEntity> sellerEntities = sellerService.getSellers();
        Random rand = new Random();
        RequestEntity currentRequest = new RequestEntity();
        for (int i = 0; i < requestEntities.size(); i++){
            currentRequest = requestEntities.get(i);
            if (currentRequest.getSellerId() == 0){
                currentRequest.setSellerId(sellerEntities.get(rand.nextInt(sellerEntities.size())).getUserID().intValue());
                currentRequest.setStatus(statusService.getStatus().get(1));
                requestRepository.save(currentRequest);
            }
        }
    }

}
