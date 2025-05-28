package stage.soutenance.projet.controllers;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import stage.soutenance.projet.entities.Demande;
import stage.soutenance.projet.entities.DocumentClient;
import stage.soutenance.projet.repositories.DocumentClientRepository;
import stage.soutenance.projet.services.FileStorageService;

@Controller
@RequestMapping("/admin")
public class AdminController {
  private final DocumentClientRepository docRepo;
  private final FileStorageService storage;

  public AdminController(DocumentClientRepository docRepo, FileStorageService storage) {
    this.docRepo = docRepo;
    this.storage = storage;
  }

  @GetMapping("/demandes/{id}")
  public String listDocs(@PathVariable Long id, Model model) {
    List<DocumentClient> docs = docRepo.findByDemandeId(id);
    model.addAttribute("docs", docs);
    model.addAttribute("demande", Demande.builder().id(id).build());
    return "DocumentView";
  }

  @GetMapping("/documents/{docId}/download")
  public ResponseEntity<Resource> download(@PathVariable Long docId) {
    DocumentClient doc = docRepo.findById(docId).orElseThrow();
    Resource file = storage.load(doc.getFilename());
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + doc.getFilename() + "\"")
        .contentType(MediaType.parseMediaType(doc.getContentType()))
        .body(file);
  }
}

