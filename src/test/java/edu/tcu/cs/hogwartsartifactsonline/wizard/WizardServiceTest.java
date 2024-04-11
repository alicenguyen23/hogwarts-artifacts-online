package edu.tcu.cs.hogwartsartifactsonline.wizard;

import edu.tcu.cs.hogwartsartifactsonline.artifact.Artifact;
import edu.tcu.cs.hogwartsartifactsonline.artifact.utils.IdWorker;
import edu.tcu.cs.hogwartsartifactsonline.system.exception.ObjectNotFoundException;
import edu.tcu.cs.hogwartsartifactsonline.wizard.Wizard;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.text.html.Option;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WizardServiceTest {

    @Mock
    WizardRepository wizardRepository;

    @InjectMocks
    WizardService wizardService;

    List<Wizard> wizards;
    List<Artifact> artifacts;

    @BeforeEach
    void setUp() {
        Artifact a1 = new Artifact();
        a1.setId("1250808601744904191");
        a1.setName("Deluminator");
        a1.setDescription("A Deluminator is a device invented by Albus Dumbledore that resembles a cigarette lighter. It is used to remove or absorb (as well as return) the light from any light source to provide cover to the user.\",\n");
        a1.setImageUrl("ImageUrl");

        Artifact a2 = new Artifact();
        a2.setId("1250808601744904192");
        a2.setName("Invisibility Cloak");
        a2.setDescription("An invisibility cloak is used to make the wearer invisible.");
        a2.setImageUrl("ImageUrl");

        Artifact a3 = new Artifact();
        a3.setId("1250808601744904193");
        a3.setName("Elder Wand");
        a3.setDescription("The Elder Wand, known throughout history as the Deathstick or the Wand of Destiny, is an extremely powerful wand made of elder wood with a core of Thestral tail hair.");
        a3.setImageUrl("ImageUrl");

        Artifact a4 = new Artifact();
        a4.setId("1250808601744904194");
        a4.setName("The Marauder's Map");
        a4.setDescription("A magical map of Hogwarts created by Remus Lupin, Peter Pettigrew, Sirius Black, and James Potter while they were students at Hogwarts.");
        a4.setImageUrl("ImageUrl");

        Artifact a5 = new Artifact();
        a5.setId("1250808601744904195");
        a5.setName("The Sword Of Gryffindor");
        a5.setDescription("A goblin-made sword adorned with large rubies on the pommel. It was once owned by Godric Gryffindor, one of the medieval founders of Hogwarts.");
        a5.setImageUrl("ImageUrl");

        Artifact a6 = new Artifact();
        a6.setId("1250808601744904196");
        a6.setName("Resurrection Stone");
        a6.setDescription("The Resurrection Stone allows the holder to bring back deceased loved ones, in a semi-physical form, and communicate with them.");
        a6.setImageUrl("ImageUrl");

        Wizard w1 = new Wizard();
        w1.setId(1);
        w1.setName("Albus dumbledore");
        w1.addArtifact(a1);
        w1.addArtifact(a3);

        Wizard w2 = new Wizard();
        w2.setId(2);
        w2.setName("Harry Potter");
        w2.addArtifact(a2);
        w2.addArtifact(a4);

        Wizard w3 = new Wizard();
        w3.setId(3);
        w3.setName("Neville Longbottom");
        w3.addArtifact(a5);

        this.wizards = new ArrayList<>();
        this.wizards.add(w1);
        this.wizards.add(w2);
        this.wizards.add(w3);

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findByIdSuccess() {
        Artifact a = new Artifact();
        a.setId("1250808601744904192");
        a.setName("Invisibility Cloak");
        a.setDescription("An invisibility cloak is used to make the wearer invisible.");
        a.setImageUrl("ImageUrl");

        Wizard w = new Wizard();
        w.setId(2);
        w.setName("Harry Potter");
        a.setOwner(w);

        given(wizardRepository.findById(2)).willReturn(Optional.of(w));

        //When. Act on the target behavior. When steps should cover the method to be tested.
        Wizard returnedWizard = wizardService.findById(2);

        //Then. Assert expected outcomes.
        assertThat(returnedWizard.getId()).isEqualTo(w.getId());
        assertThat(returnedWizard.getName()).isEqualTo(w.getName());
        assertThat(returnedWizard.getArtifacts()).isEqualTo(w.getArtifacts());

        verify(wizardRepository, times(1)).findById(2);
    }
    @Test
    void testFindByIdNotFound() {
        //Given
        given(wizardRepository.findById(Mockito.any(Integer.class))).willReturn(Optional.empty());

        //When
        Throwable thrown = catchThrowable(()->{
            Wizard returnedWizard = wizardService.findById(5);
        });
        //Then
        assertThat(thrown).
                isInstanceOf(ObjectNotFoundException.class).
                hasMessage("Could not find artifact with Id 5 :(");

        verify(wizardRepository, times(1)).findById(5);
    }

    @Test
    void testFindALlSuccess() {
        //Given
        given(wizardRepository.findAll()).willReturn(this.wizards);
        //When
        List<Wizard> actualWizards = wizardService.findAll();
        //Then
        assertThat(actualWizards.size()).isEqualTo(this.wizards.size());

        verify(wizardRepository, times(1)).findAll();
    }

    @Test
    void testSaveSuccess() {
        //Given
        Wizard newWizard = new Wizard();
        newWizard.setName("Wizard 4");
        newWizard.setId(4);
        newWizard.setArtifacts(null);

        given(wizardRepository.save(newWizard)).willReturn(newWizard);

        //When
        Wizard savedWizard = wizardService.save(newWizard);

        //Then
        assertThat(savedWizard.getId()).isEqualTo(4);
        assertThat(savedWizard.getName()).isEqualTo(newWizard.getName());
        assertThat(savedWizard.getArtifacts()).isEqualTo(null);

        verify(wizardRepository, times(1)).save(newWizard);
    }

    @Test
    void testUpdateSuccess() {
        //Given
        Artifact a2 = new Artifact();
        a2.setId("1250808601744904192");
        a2.setName("Invisibility Cloak");
        a2.setDescription("An invisibility cloak is used to make the wearer invisible.");
        a2.setImageUrl("ImageUrl");

        Wizard oldWizard = new Wizard();
        oldWizard.setId(2);
        oldWizard.setName("Harry Potter");
        oldWizard.addArtifact(a2);

        Wizard newWizard = new Wizard();
        newWizard.setId(2);
        newWizard.setName("Ron Weasley");
        newWizard.addArtifact(a2);

        given(wizardRepository.findById(2)).willReturn(Optional.of(oldWizard));
        given(wizardRepository.save(oldWizard)).willReturn(oldWizard);

        //When
        Wizard updatedWizard = wizardService.update(2, newWizard);

        //Then
        assertThat(updatedWizard.getId()).isEqualTo(newWizard.getId());
        assertThat(updatedWizard.getName()).isEqualTo(newWizard.getName());
        assertThat(updatedWizard.getArtifacts()).isEqualTo(newWizard.getArtifacts());

        verify(wizardRepository, times(1)).findById(2);
        verify(wizardRepository, times(1)).save(oldWizard);

    }

    @Test
    void testUpdateNotFound() {
        //Given
        Wizard update = new Wizard();
        update.setId(2);
        update.setName("Harry Potter");

        given(wizardRepository.findById(2)).willReturn(Optional.empty());

        //When
        assertThrows(ObjectNotFoundException.class, () -> {
            wizardService.update(2, update);
        });

        //Then
        verify(wizardRepository, times(1)).findById(2);

    }

    @Test
    void testDeleteSuccess() {
        //Given
        Wizard w = new Wizard();
        w.setId(2);
        w.setName("Harry Potter");

        given(wizardRepository.findById(2)).willReturn(Optional.of(w));
        doNothing().when(wizardRepository).deleteById(2);

        //When
        wizardService.delete(2);

        //Then
        verify(wizardRepository, times(1)).deleteById(2);

    }

    @Test
    void testDeleteNotFound() {
        //Given
        given(wizardRepository.findById(2)).willReturn(Optional.empty());

        //When
        assertThrows(ObjectNotFoundException.class, () -> {
            wizardService.delete(2);
        });
        //Then
        verify(wizardRepository, times(1)).findById(2);

    }
}