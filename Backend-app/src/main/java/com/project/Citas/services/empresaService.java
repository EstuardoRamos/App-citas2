package com.project.Citas.services;

import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.Citas.Errors.ModelNotFoundException;
import com.project.Citas.Images.DropBoxImage;
import com.project.Citas.Images.ImageService;
import com.project.Citas.models.EmpresaModel;
import com.project.Citas.models.Empresa.diasAtencionModel;
import com.project.Citas.repositories.EmpresaRepository;
import com.project.Citas.repositories.diasAtencionRepository;

@Service
public class empresaService {
    @Autowired
    @Mock
    private EmpresaRepository empresaRepositorie;
    @Autowired
    @Mock
    private diasAtencionRepository diasAtencionRepositorie;
    public Optional<EmpresaModel> getEmpresaById(Long id) {
        return empresaRepositorie.findById(id);
    }

    public void saveHorarios(ArrayList<diasAtencionModel> horarios) {
        diasAtencionRepositorie.saveAll(horarios);
    }

    public ArrayList<diasAtencionModel> getHorarios() {
        return (ArrayList<diasAtencionModel>) (diasAtencionRepositorie.findAll());
    }

    public String getBase64(){
        Optional<EmpresaModel> empresaModel = empresaRepositorie.findById(1L);
        if(empresaModel.isPresent()){
            ImageService image = new ImageService();
            String url =  empresaModel.get().getUrlImagen();
            return image.convertImageToBase64(url);
        }else
            throw new ModelNotFoundException("La empresa no existe");
    }

    public EmpresaModel saveEmpresa(EmpresaModel empresaModel, MultipartFile imagen) {
        if (empresaRepositorie.findById(empresaModel.getId()).isPresent()) {
            System.out.println("nombre:  " + empresaModel.getNombre());
            if(imagen!=null)
            empresaModel.setUrlImagen(DropBoxImage.Cargar(imagen));
            return empresaRepositorie.save(empresaModel);
        } else
            throw new ModelNotFoundException(
                    "Los datos iniciales de la empresa aun no han sido creados en la base de datos");
    }

}
