package stage.soutenance.projet.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.net.MalformedURLException;
import org.springframework.util.StringUtils;


@Service
public class FileStorageService {
  private final Path root;

  public FileStorageService(@Value("${file.upload-dir}") String dir) {
    this.root = Paths.get(dir).toAbsolutePath().normalize();
    try { Files.createDirectories(root); }
    catch(IOException e) { throw new RuntimeException("Impossible de créer le dossier", e); }
  }

  public String store(MultipartFile file) {
    String originalName = file.getOriginalFilename();
    if (originalName == null) {
        throw new RuntimeException("Le nom du fichier est nul.");
    }
    String name = StringUtils.cleanPath(originalName);

    if(name.contains("..")) throw new RuntimeException("Nom de fichier invalide");
    try (InputStream in = file.getInputStream()) {
      Files.copy(in, root.resolve(name), StandardCopyOption.REPLACE_EXISTING);
      return name;
    } catch(IOException e) {
      throw new RuntimeException("Échec stockage " + name, e);
    }
  }

  public Resource load(String filename) {
    try {
      Path file = root.resolve(filename).normalize();
      Resource res = new UrlResource(file.toUri());
      if(res.exists()||res.isReadable()) return res;
      else throw new RuntimeException("Fichier non trouvable");
    } catch(MalformedURLException e) {
      throw new RuntimeException("Erreur lecture fichier", e);
    }
  }
}

