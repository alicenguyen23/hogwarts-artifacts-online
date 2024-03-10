package edu.tcu.cs.hogwartsartifactsonline.artifact;

import com.fasterxml.jackson.core.json.async.NonBlockingUtf8JsonParserBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtifactRepository extends JpaRepository<Artifact, String> {
}
