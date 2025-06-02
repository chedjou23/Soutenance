package stage.soutenance.projet.controllers;

import java.time.Instant;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import stage.soutenance.projet.entities.Demande;
import stage.soutenance.projet.entities.DocumentClient;
import stage.soutenance.projet.entities.TypeDocument;
import stage.soutenance.projet.repositories.DemandeRepository;
import stage.soutenance.projet.repositories.DocumentClientRepository;
import stage.soutenance.projet.services.FileStorageService;

@Controller
@RequestMapping("/clients")
public class DemandeController {
  private final FileStorageService storage;
  private final DemandeRepository dr;
  private final DocumentClientRepository docRepo;

  public DemandeController(FileStorageService storage,
                           DemandeRepository dr,
                           DocumentClientRepository docRepo) {
    this.storage = storage;
    this.dr = dr;
    this.docRepo = docRepo;
  }

  @GetMapping("/{id}/upload")
  public String showUploadInDashboard(@PathVariable Long id, Model model) {
      Demande demande = dr.findById(id).orElseThrow();
      model.addAttribute("demande", demande);
      model.addAttribute("types", TypeDocument.values());
      model.addAttribute("documents", docRepo.findByDemandeId(id));

      return "uploadView";
  }


  @PostMapping("/{id}/upload")
  public String handleUpload(@PathVariable Long id,
                             @RequestParam("type") TypeDocument type,
                             @RequestParam("file") MultipartFile file,
                             RedirectAttributes ra) {
    Demande d = dr.findById(id).orElseThrow();
    String filename = storage.store(file);
    docRepo.save(DocumentClient.builder()
      .demande(d)
      .type(type)
      .filename(filename)
      .contentType(file.getContentType())
      .uploadedAt(Instant.now())
      .build());
    ra.addFlashAttribute("message", "Fichier "+filename+" téléversé!");
    return "redirect:/clients/"+id+"/upload";
  }
}

