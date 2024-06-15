package com.civilo.roller.services;

import com.civilo.roller.Entities.QuoteEntity;
import com.civilo.roller.Entities.QuoteSummaryEntity;
import com.civilo.roller.Entities.SellerEntity;
import com.civilo.roller.repositories.QuoteSummaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.civilo.roller.exceptions.EntityNotFoundException;
import java.util.Optional;
import java.util.Date;
import java.util.List;

@Service
public class QuoteSummaryService {
    @Autowired
    QuoteSummaryRepository quoteSummaryRepository;

    @Autowired
    IVAService ivaService;

    // Get all
    // El siguiente método retorna un listado el cual contiene TODA la información asociada a los resumenes de cotizacion
    public List<QuoteSummaryEntity> getQuoteSummarys(){
        return (List<QuoteSummaryEntity>) quoteSummaryRepository.findAll();
    }

    // Get by id
    // Permite obtener la informacion de un resumen de cotizacion en especifico.
    public Optional<QuoteSummaryEntity> getQuoteSummaryById(Long id){
        return quoteSummaryRepository.findById(id);
    }

    // Permite guardar un objeto del tipo "QuoteSummaryEntity" en la base de datos.
    public QuoteSummaryEntity saveQuoteSummary(QuoteSummaryEntity quoteSummary){
        return quoteSummaryRepository.save(quoteSummary);
    }

    // Create
    // Permite guardar un objeto del tipo "QuoteSummaryEntity" en la base de datos.
    public QuoteSummaryEntity createQuoteSummary(QuoteSummaryEntity quoteSummary){   
        return quoteSummaryRepository.save(quoteSummary);  
    }

    // Update
    // Permite actualizar los datos de un objeto del tipo "QuoteSummaryEntity" en la base de datos.
    public QuoteSummaryEntity updateQuoteSummary(Long quoteSummaryID, QuoteSummaryEntity quoteSummary){
        QuoteSummaryEntity existingQuoteSummary = quoteSummaryRepository.findById(quoteSummaryID)
            .orElseThrow(() -> new EntityNotFoundException("Resumen de cotizacion no encontrado con el ID: " + quoteSummaryID));

        //existingQuoteSummary.setPermission(quoteSummary.getPermission()); //No deberia poder cambiarse
        
        QuoteSummaryEntity updatedQuoteSummary = quoteSummaryRepository.save(existingQuoteSummary);
        return updatedQuoteSummary;
    }

    // Delete all
    // Permite eliminar todos los resumenes de cotizacion del sistema.
    public void deleteQuoteSummary() {
        quoteSummaryRepository.deleteAll();
    }

    // Delete by id
    // Permite eliminar un resumen de cotizacion en especifico del sistema.
    public void deleteQuoteSummaryById(Long id){
        quoteSummaryRepository.deleteById(id);
    }
    
    // Permite verificar si existe un resumen de cotizacion en el sistema, segun el id ingresado.
    public boolean existsQuoteSummaryById(Long id){
        return quoteSummaryRepository.findById(id).isPresent();
    }


    // Permite realizar cálculos del resumen de cotización
    public QuoteSummaryEntity summaryCalculation(List<QuoteEntity> quoteEntities, Long quoteSummaryID){
        QuoteSummaryEntity quoteSummary = new QuoteSummaryEntity();
        float totalCostOfProduction = 0, totalSaleValue = 0, valueAfterDiscount = 0, discountPercentage = 0, totalNet = 0, iva = 0, ivaPercentage = 0, total = 0;
        Date date = null;
        SellerEntity seller = null;
        for (int i = 0; i < quoteEntities.size(); i++) {
            totalCostOfProduction += quoteEntities.get(i).getProductionCost();
            totalSaleValue += quoteEntities.get(i).getSaleValue();
            discountPercentage = quoteEntities.get(i).getPercentageDiscount();
            date = quoteEntities.get(i).getDate();
            seller = quoteEntities.get(i).getSeller();
        }
        ivaPercentage = ivaService.getLastIVA().getIvaPercentage();
        valueAfterDiscount = totalSaleValue;
        totalNet = totalSaleValue;
        if (discountPercentage != 0) {
            valueAfterDiscount = (float) Math.ceil(totalSaleValue * (discountPercentage / 100));
            totalNet = totalSaleValue - valueAfterDiscount;
        }
        iva = totalNet;
        if (ivaPercentage != 0) {
            iva = (float) Math.ceil(totalNet * (ivaPercentage / 100));
        }
        total = (float) Math.ceil(totalNet * (1 + ivaPercentage / 100));
        quoteSummary.setDate(date);
        quoteSummary.setSeller(seller);
        quoteSummary.setTotalCostOfProduction((int) Math.ceil(totalCostOfProduction));
        quoteSummary.setTotalSaleValue((int) Math.ceil(totalSaleValue));
        quoteSummary.setValueAfterDiscount((int) Math.ceil(valueAfterDiscount));
        quoteSummary.setNetTotal((int) Math.ceil(totalNet));
        quoteSummary.setTotal((int) Math.ceil(total));
        quoteSummary.setCurrentIVA(ivaService.getIVAByPercentage(ivaPercentage));

        if (quoteSummaryID != null){
            quoteSummary.setQuoteSummaryID(quoteSummaryID);
        }
        return quoteSummaryRepository.save(quoteSummary);
    }
}