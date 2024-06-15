package com.civilo.roller.services;

import com.civilo.roller.Entities.CurtainEntity;
import com.civilo.roller.Entities.PipeEntity;
import com.civilo.roller.Entities.ProfitMarginEntity;
import com.civilo.roller.Entities.QuoteEntity;
import com.civilo.roller.Entities.SellerEntity;
import com.civilo.roller.Entities.RequestEntity;
import com.civilo.roller.Entities.QuoteSummaryEntity;
import com.civilo.roller.repositories.QuoteRepository;
import com.civilo.roller.repositories.QuoteSummaryRepository;
import com.civilo.roller.repositories.ProfitMarginRepository;
import com.civilo.roller.repositories.CurtainRepository;
import com.civilo.roller.repositories.SellerRepository;
import com.civilo.roller.repositories.RequestRepository;
import com.civilo.roller.repositories.PipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import com.civilo.roller.exceptions.EntityNotFoundException;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.List;

@Service
public class QuoteService {
    @Autowired
    QuoteRepository quoteRepository;

    @Autowired
    QuoteSummaryRepository quoteSummaryRepository;

    @Autowired
    ProfitMarginService profitMarginService;

    @Autowired
    CurtainRepository curtainRepository;

    @Autowired 
    PipeRepository pipeRepository;

    @Autowired 
    ProfitMarginRepository profitMarginRepository;

    @Autowired 
    SellerRepository sellerRepository;

    @Autowired 
    RequestRepository requestRepository;

    // Get all
    // Permite obtener un listado con toda la informacion asociada a las solicitudes.
    public List<QuoteEntity> getQuotes() {
        return (List<QuoteEntity>) quoteRepository.findAll();
    }

    // Get by id
    // Permite obtener la informacion de una solicitud en especifico.
    public Optional<QuoteEntity> getQuoteById(Long id) {
        return quoteRepository.findById(id);
    }

    // Permite guardar un objeto del tipo "QuoteEntity" en la base de datos.
    public QuoteEntity saveQuote(QuoteEntity quote) {
        return quoteRepository.save(quote);
    }

    // Create
    // Permite guardar un objeto del tipo "QuoteEntity" en la base de datos.
    public QuoteEntity createQuote(QuoteEntity quote) {
        return quoteRepository.save(quote);
    }

    // Update
    // Permite actualizar los datos de un objeto del tipo "QuoteEntity" en la base de datos.
     
    public QuoteEntity updateQuote(Long quoteID, QuoteEntity quote){

        QuoteEntity existingQuote = quoteRepository.findById(quoteID)
            .orElseThrow(() -> new EntityNotFoundException("Cotizacion no encontrada con el ID: " + quoteID));

        existingQuote.setAmount(quote.getAmount());
        existingQuote.setAssemblyValue(quote.getAssemblyValue());
        existingQuote.setBandValue(quote.getBandValue());
        existingQuote.setBracketValue(quote.getBracketValue());
        existingQuote.setCapValue(quote.getCapValue());
        existingQuote.setChainValue(quote.getChainValue());
        existingQuote.setCounterweightValue(quote.getCounterweightValue());
        existingQuote.setDate(quote.getDate());
        existingQuote.setHeight(quote.getHeight());
        existingQuote.setInstallationValue(quote.getInstallationValue());
        existingQuote.setPercentageDiscount(quote.getPercentageDiscount());
        existingQuote.setPipeValue(quote.getPipeValue());
        existingQuote.setProductionCost(quote.getProductionCost());
        existingQuote.setSaleValue(quote.getSaleValue());
        existingQuote.setTotalFabrics(quote.getTotalFabrics());
        existingQuote.setTotalLabor(quote.getTotalLabor());
        existingQuote.setTotalMaterials(quote.getTotalMaterials());
        existingQuote.setTotalSquareMeters(quote.getTotalSquareMeters());
        existingQuote.setValueSquareMeters(quote.getValueSquareMeters());
        existingQuote.setWidth(quote.getWidth());

        CurtainEntity existingCurtain = curtainRepository.findById(quote.getCurtain().getCurtainID())
            .orElseThrow(() -> new EntityNotFoundException("Cortina no encontrada con el ID: " + quote.getCurtain().getCurtainID()));
        Long newCurtainId = quote.getCurtain().getCurtainID();
        CurtainEntity newCurtain = curtainRepository.findById(newCurtainId)
            .orElseThrow(() -> new EntityNotFoundException("Cortina no encontrada con el ID: " + newCurtainId));
        existingQuote.setCurtain(newCurtain);

        PipeEntity existingPipe = pipeRepository.findById(quote.getPipe().getPipeID())
            .orElseThrow(() -> new EntityNotFoundException("Tubo no encontrado con el ID: " + quote.getPipe().getPipeID()));
        Long newPipeId = quote.getPipe().getPipeID();
        PipeEntity newPipe = pipeRepository.findById(newPipeId)
            .orElseThrow(() -> new EntityNotFoundException("Tubo no encontrado con el ID: " + newPipeId));
        existingQuote.setPipe(newPipe);
        
        ProfitMarginEntity existingProfitMargin = profitMarginRepository.findById(quote.getProfitMarginEntity().getProfitMarginID())
            .orElseThrow(() -> new EntityNotFoundException("Margen de beneficio no encontrado con el ID: " + quote.getProfitMarginEntity().getProfitMarginID()));
        Long newProfitMarginId = quote.getProfitMarginEntity().getProfitMarginID();
        ProfitMarginEntity newProfitMargin = profitMarginRepository.findById(newProfitMarginId)
            .orElseThrow(() -> new EntityNotFoundException("Margen de beneficio no encontrado con el ID: " + newProfitMarginId));
        existingQuote.setProfitMarginEntity(newProfitMargin);

        QuoteSummaryEntity existingQuoteSummary = quoteSummaryRepository.findById(quote.getQuoteSummary().getQuoteSummaryID())
            .orElseThrow(() -> new EntityNotFoundException("Resumen de cotizacion no encontrado con el ID: " + quote.getQuoteSummary().getQuoteSummaryID()));
        Long newQuoteSummaryId = quote.getQuoteSummary().getQuoteSummaryID();
        QuoteSummaryEntity newQuoteSummary = quoteSummaryRepository.findById(newQuoteSummaryId)
            .orElseThrow(() -> new EntityNotFoundException("Resumen de cotizacion no encontrado con el ID: " + newQuoteSummaryId));
        existingQuote.setQuoteSummary(newQuoteSummary);

        SellerEntity existingSeller = sellerRepository.findById(quote.getSeller().getUserID())
            .orElseThrow(() -> new EntityNotFoundException("Vendedor no encontrado con el ID: " + quote.getSeller().getUserID()));
        Long newSellerId = quote.getSeller().getUserID();
        SellerEntity newSeller = sellerRepository.findById(newSellerId)
            .orElseThrow(() -> new EntityNotFoundException("Vendedor no encontrado con el ID: " + newSellerId));
        existingQuote.setSeller(newSeller);

        RequestEntity existingRequest = requestRepository.findById(quote.getRequestEntity().getRequestID())
            .orElseThrow(() -> new EntityNotFoundException("Solicitud no encontrada con el ID: " + quote.getRequestEntity().getRequestID()));
        Long newRequestId = quote.getRequestEntity().getRequestID();
        RequestEntity newRequest = requestRepository.findById(newRequestId)
            .orElseThrow(() -> new EntityNotFoundException("Solicitud no encontrada con el ID: " + newRequestId));
        existingQuote.setRequestEntity(newRequest);

        QuoteEntity updatedQuote = quoteRepository.save(existingQuote);
        return updatedQuote;
    }
    
 
    // Delete all
    // Permite eliminar todas las cotizaciones de un sistema.
    public void deleteQuotes() {
        quoteRepository.deleteAll();
    }

    // Delete by id
    // Permite eliminar una cotizacion en especifico del sistema.
    public void deleteQuoteById(Long id) {
        quoteRepository.deleteById(id);
    }

    // Permite verificar si existe una cotizacion en el sistema, segun el id ingresado.
    public boolean existsQuoteById(Long id) {
        return quoteRepository.findById(id).isPresent();
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------//

    //Función que permite extraer/cálcular y settear las variables referentes a la fecha, total en metros
    //cuadrados (área), valor total de telas (fabrics), valor total de materiales, valor total de mano
    //de obra (labor), valor total de producción y valor total de venta
    public void calculation(QuoteEntity quote) {
        Date currentDate = new Date();
        quote.setDate(currentDate);
        quote.setTotalSquareMeters((int) Math.ceil(quote.getHeight() * quote.getWidth() * quote.getAmount()));
        quote.setProfitMarginEntity(profitMarginService.getLastProfitMargin());
        System.out.println("Área (m2)                   = Ancho (m) × Alto (m) × Cantidad de cortinas                                                    = " + (int) Math.ceil(quote.getHeight() * quote.getWidth() * quote.getAmount()));
        quote.setTotalFabrics((int) Math.ceil(quote.getHeight() * quote.getValueSquareMeters() * quote.getAmount()));
        System.out.println("Total en telas (CLP)        = Alto (m) × Valor tela (m2) × Cantidad de cortinas                                              = " + (int) Math.ceil(quote.getHeight() * quote.getValueSquareMeters() * quote.getAmount()));
        quote.setTotalMaterials((int) Math.ceil(
                (calculateBracket(quote.getBracketValue(), quote.getAmount()) +
                        calculateCap(quote.getCapValue(), quote.getAmount()) +
                        calculatePipe(quote.getPipeValue(), quote.getAmount(), quote.getWidth()) +
                        calculateCounterweight(quote.getCounterweightValue(), quote.getAmount(), quote.getWidth()) +
                        calculateBand(quote.getBandValue(), quote.getAmount(), quote.getWidth()) +
                        calculateChain(quote.getChainValue(), quote.getAmount(), quote.getWidth()))
        ));
        System.out.println("Total en materiales (CLP)   = Valor brackets + Valor tapas + Valor tubos + Valor contrapesos + Valor zunchos + Valor cadenas = " +
                (int) Math.ceil(
                        (calculateBracket(quote.getBracketValue(), quote.getAmount()) +
                                calculateCap(quote.getCapValue(), quote.getAmount()) +
                                calculatePipe(quote.getPipeValue(), quote.getAmount(), quote.getWidth()) +
                                calculateCounterweight(quote.getCounterweightValue(), quote.getAmount(), quote.getWidth()) +
                                calculateBand(quote.getBandValue(), quote.getAmount(), quote.getWidth()) +
                                calculateChain(quote.getChainValue(), quote.getAmount(), quote.getWidth()))
                )
        );
        quote.setTotalLabor((int) Math.ceil((quote.getAssemblyValue() + quote.getInstallationValue()) * quote.getAmount()));
        System.out.println("Total en mano de obra (CLP) = (Valor armado + Valor instalación) × Cantidad                                                  = " + (int) Math.ceil((quote.getAssemblyValue() + quote.getInstallationValue()) * quote.getAmount()));
        quote.setProductionCost((int) Math.ceil(quote.getTotalLabor() + quote.getTotalMaterials() + quote.getTotalFabrics()));
        System.out.println("Costo de producción (CLP)   = Total en mano de obra + Total en materiales + Total en telas                                   = " + (int) Math.ceil((quote.getTotalLabor() + quote.getTotalMaterials() + quote.getTotalFabrics())));
        quote.setSaleValue((int) Math.ceil((quote.getProductionCost() / (1 - profitMarginService.getLastProfitMargin().getDecimalProfitMargin()))));
        System.out.println("Valor de venta (CLP)        = Costo de producción ( 1 - Margen de utilidad)                                                  = " + (int) Math.ceil((quote.getProductionCost() / (1 - profitMarginService.getLastProfitMargin().getDecimalProfitMargin()))));
    }

    public float calculateBracket(float bracket, int amount) {
        return bracket * amount;
    }

    public float calculateCap(float cap, int amount) {
        return cap * amount;
    }

    public float calculatePipe(float pipe, int amount, float width) {
        return pipe * amount * width;
    }

    public float calculateBand(float band, int amount, float width) {
        return band * amount * width;
    }

    public float calculateChain(float chain, int amount, float width) {
        return chain * amount * width;
    }

    public float calculateCounterweight(float counterWeight, int amount, float width) {
        return counterWeight * amount * width;
    }


    // Permite obtener un listado de todas las cotizaciones asociadas a un vendedor especifico.
    public List<QuoteEntity> sellerQuotes(Long idSeller) {
        List<QuoteEntity> quoteEntities = (List<QuoteEntity>) quoteRepository.findAll();
        List<QuoteEntity> quoteSelected = new ArrayList<>();
        for (int i = 0; i < quoteEntities.size(); i++) {
            if(quoteEntities.get(i).getSeller() != null) {
                if (quoteEntities.get(i).getSeller().getUserID() == idSeller) {
                    quoteSelected.add(quoteEntities.get(i));
                }
            }
        }
        return quoteSelected;
    }


    // Función que recibe una lista de elementos QuoteEntity para agregarlos a la base de datos uno a uno
    public void createQuotes(List<QuoteEntity> quoteList) {
        for (int i = 0; i < quoteList.size(); i++) {
            quoteRepository.save(quoteList.get(i));
        }
    }

    
    // Permite obtener un listado de todos los resumenes de cotizaciones
    public List<QuoteSummaryEntity> listQuoteSummary(Long idSeller) {
        List<QuoteSummaryEntity> quoteSummaryEntities = (List<QuoteSummaryEntity>) quoteSummaryRepository.findAll();
        List<QuoteSummaryEntity> quoteSummarySelected = new ArrayList<>();
        for (int i = 0; i < quoteSummaryEntities.size(); i++) {
            if(quoteSummaryEntities.get(i).getSeller() != null) {
                if (quoteSummaryEntities.get(i).getSeller().getUserID() == idSeller) {
                    quoteSummarySelected.add(quoteSummaryEntities.get(i));
                }
            }     
        }
        return quoteSummarySelected;
    }


    // Permite encontrar el unico resumen de una cotizacion asociado a la solicitud seleccionada
    public QuoteSummaryEntity findQuoteSummary(List<QuoteSummaryEntity> listSummary, Long idQuoteSelected, Long idSeller) {
        List<QuoteEntity> quoteEntities = (List<QuoteEntity>) quoteRepository.findAll();
        QuoteSummaryEntity quoteSummary = new QuoteSummaryEntity();

        for (int i = 0; i < listSummary.size(); i++) {
            for(int j = 0; j < quoteEntities.size(); j++) {
                if( listSummary.get(i).getQuoteSummaryID() == quoteEntities.get(j).getQuoteSummary().getQuoteSummaryID() ) {
                    if(quoteEntities.get(j).getRequestEntity() != null) {
                        if ( ( idQuoteSelected == quoteEntities.get(j).getRequestEntity().getRequestID() ) && (idSeller == quoteEntities.get(j).getSeller().getUserID() ) ) {
                            quoteSummary = listSummary.get(i);
                            return quoteSummary;   
                        }
                    } 
                }
            }
        }
        return quoteSummary;
    }


    // Permite encontrar un listado de las cotizaciones asignadas a la misma solicitud
    public List<QuoteEntity> listQuotes(QuoteSummaryEntity summarySelected, Long idQuoteSelected, Long idSeller) {
        List<QuoteEntity> quoteEntities = (List<QuoteEntity>) quoteRepository.findAll();
        List<QuoteEntity> quoteEntitiesSelected = new ArrayList<>();
        
        for (int i = 0; i < quoteEntities.size(); i++) {
            if( summarySelected.getQuoteSummaryID() == quoteEntities.get(i).getQuoteSummary().getQuoteSummaryID() ) {
                if(quoteEntities.get(i).getRequestEntity() != null) {
                    if ( ( idQuoteSelected == quoteEntities.get(i).getRequestEntity().getRequestID() ) && (idSeller == quoteEntities.get(i).getSeller().getUserID() ) ) {
                        quoteEntitiesSelected.add(quoteEntities.get(i));
                    }
                }
            }
        }
        return quoteEntitiesSelected;
    }
     
 
    // Permite determinar si se incluye la instalacion de la cortina roller en base al valor de instalacion de la cotizacion
    public String instalation(List<QuoteEntity> quotes){
        for (int i = 0; i < quotes.size(); i++){
            if (quotes.get(i).getInstallationValue() != 0){
                return "Si";
            }
        }
        return "No";
    }

    public Long existQuoteSummaryWithMyInfo(List<QuoteEntity> quoteEntities){
        List<QuoteEntity> quoteEntityList = getQuotes();
        for (int i = 0; i < quoteEntityList.size(); i++){
            if (quoteEntities.get(0).getRequestEntity().getRequestID() == quoteEntityList.get(i).getRequestEntity().getRequestID() ){
                return quoteEntityList.get(i).getQuoteSummary().getQuoteSummaryID();
            }
        }
        return null;
    }

    public void updateQuotesWithMyInfo(List<QuoteEntity> quoteEntities){
        List<Long> idList = new ArrayList<>();
        List<QuoteEntity> quoteEntityList = getQuotes();
        for (int i = 0; i < quoteEntityList.size(); i++){
            for (int j = 0; j < quoteEntities.size(); j++){
                if (quoteEntities.get(j).getRequestEntity().getRequestID() == quoteEntityList.get(i).getRequestEntity().getRequestID() &&
                    !idList.contains(quoteEntityList.get(i).getQuoteID())){
                    idList.add(quoteEntityList.get(i).getQuoteID());
                }
            }
        }
        if (!idList.isEmpty()){
            for (int i = 0; i < idList.size(); i++){
                if (quoteEntities.size() <= i) {
                    deleteQuoteById(idList.get(i));
                }
                else {
                    quoteEntities.get(i).setQuoteID(idList.get(i));
                    saveQuote(quoteEntities.get(i));
                }
                System.out.println(i);

            }
        }
        for (int i = 0; i < quoteEntities.size(); i++){
            saveQuote(quoteEntities.get(i));
        }
    }

}


