package com.epicode.progettofinaleepicode.auth.runners;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.epicode.progettofinaleepicode.auth.entity.ERole;
import com.epicode.progettofinaleepicode.auth.entity.Role;
import com.epicode.progettofinaleepicode.auth.entity.Utente;
import com.epicode.progettofinaleepicode.auth.repository.RoleRepository;
import com.epicode.progettofinaleepicode.auth.repository.UserRepository;
import com.epicode.progettofinaleepicode.entity.Jersey;
import com.epicode.progettofinaleepicode.entity.Partite;
import com.epicode.progettofinaleepicode.entity.Squadre;
import com.epicode.progettofinaleepicode.repository.JerseyRepository;
import com.epicode.progettofinaleepicode.repository.PartiteRepository;
import com.epicode.progettofinaleepicode.repository.SquadreRepository;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@Component
public class UtenteRunner implements ApplicationRunner {


	JerseyRepository jerseyRepository;
	SquadreRepository squadraRepository;
	PartiteRepository partiteRepository;
	RoleRepository roleRepository;
	UserRepository userRepository;
	PasswordEncoder encoder;
	

	@Override
	public void run(ApplicationArguments args) throws Exception {
		Role admin = new Role();
		admin.setRoleName( ERole.ROLE_ADMIN);
		roleRepository.save(admin);
		
		Role user = new Role();
		user.setRoleName( ERole.ROLE_USER);
		roleRepository.save(user);
		
		Set<Role> ruoliAdministrator = new HashSet<Role>();
		ruoliAdministrator.add(admin);
		
		Utente userAdmin  = new Utente();
		userAdmin.setUsername("craigbauer89");

		userAdmin.setPassword(encoder.encode("qwerty"));
//		userAdmin.setNome("Timmy");
//		userAdmin.setEmail("timmyv@gmail.com");
//		userAdmin.setCognome("Verde");
		userAdmin.setRoles(ruoliAdministrator);
		userRepository.save(userAdmin);
		
		Set<Role> ruoliUtente = new HashSet<Role>();
		ruoliUtente.add(user);
		
		Utente simpleUser = new Utente();
		simpleUser.setUsername("user1");
//		simpleUser.setNome("gianluigi");
//		simpleUser.setEmail("gianluigi@gmail.com");
//		simpleUser.setCognome("Marrone");
		simpleUser.setPassword( encoder.encode("qwerty"));

		simpleUser.setRoles(ruoliUtente);
		userRepository.save(simpleUser);
		
		
//		Utente simpleUser2 = new Utente();
//		simpleUser2.setUsername("user1");
//		simpleUser2.setPassword( encoder.encode("qwerty"));
//		userRepository.save(simpleUser2);
		
		String[] colors = {"red", "white", "purple", "wine","bluedarkblue", "black", "blueyellow", "redorange", "blackgreen",
				"redblue", "redwhite", "whiteblue", "yellowgreen","whitedarkblue", "greenblue", "redblackyellow", "greenyellowblack","blueredwhite" , "blackgreen" , "blackyellowwhite" ,"blackwhite", "extracolor"};
		
		for (String color : colors) {
			  
		
		Jersey jersey  = new Jersey();
		jersey.setColor(color);
		jerseyRepository.save(jersey);
		
		
		
		}
		
		List<Squadre> squadre = readSquadreFromCSV("squadre3.csv"); 
		
		for (Squadre s : squadre) { 
			System.out.println(s);
			squadraRepository.save(s);
			}
		
		List<Partite> partite = readPartiteFromCSV("partite.csv"); 
		
		for (Partite p : partite) { 
			System.out.println(p);
			partiteRepository.save(p);
			}
		
		
		
	}
			 
	private static List<Partite> readPartiteFromCSV(String fileName) { 
		List<Partite> partite = new ArrayList<>();
		Path pathToFile = Paths.get(fileName); 
		
		try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {
			
			String line = br.readLine();
			
			while (line != null) { 
				String[] attributes = line.split(","); 
				Partite partita = createPartita(attributes); 
				partite.add(partita); // read next line before looping // if end of file reached, line would be null 
				line = br.readLine(); 
				} 
			} catch (IOException ioe) { 
				ioe.printStackTrace();
			} 
		return partite; 
		
		} 
		
		private static List<Squadre> readSquadreFromCSV(String fileName) { 
			List<Squadre> squadre = new ArrayList<>();
			Path pathToFile = Paths.get(fileName); 
			
			try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {
				
				String line = br.readLine();
				
				while (line != null) { 
					String[] attributes = line.split(","); 
					Squadre squadra = createSquadra(attributes); 
					squadre.add(squadra); // read next line before looping // if end of file reached, line would be null 
					line = br.readLine(); 
					} 
				} catch (IOException ioe) { 
					ioe.printStackTrace();
				} 
			return squadre; 
			
			} 
		
		private static Squadre createSquadra(String[] metadata) { 
			
			String[] colors = {"red", "white", "purple", "wine","bluedarkblue", "black", "blueyellow", "redorange", "blackgreen",
					"redblue", "redwhite", "whiteblue", "yellowgreen","whitedarkblue", "greenblue", "redblackyellow","greenyellowblack","blueredwhite","blackgreen" ,"blackyellowwhite" ,"blackwhite", "extracolor"};
			
			Long id = new Long(metadata[0]);
			BigDecimal latitude = new BigDecimal(metadata[1]);
			BigDecimal longitude =  new BigDecimal(metadata[2]);
			 String nome  =  metadata[3];
			 String allenatore = metadata[4];
			 String indirizzo  = metadata[5];
			 String sito  =  metadata[6];
			 String telefono   =  metadata[7];
			 Jersey jersey  =  new Jersey(new Long(metadata[8]), colors[ new Integer(metadata[8])]);
			 Integer puntiFatti = new Integer(metadata[15]); //done
			 Integer meteFatti = new Integer(metadata[14]); //done
			 Integer puntiSubiti = new Integer(metadata[16]); //done
			 Integer meteSubiti = new Integer(metadata[18]); //done
			 Integer vittorie = new Integer(metadata[11]); //done
			 Integer pareggi = new Integer(metadata[12]); //done
			 Integer sconfitte = new Integer(metadata[13]); //done
			 Integer giocate = new Integer(metadata[10]); //done
			 Integer punti = new Integer(metadata[9]); // done
			 Integer differenza = new Integer(metadata[17]); //done
			 Integer girone = new Integer(metadata[19]); //done
			 
			 
			 
//			 65,9,26,2,2,0,0,2,8,39
			 
				
			Squadre squadre  = new Squadre();
			squadre.setId(id);
			squadre.setLatitude(latitude);
			squadre.setLongitude(longitude);
			squadre.setNome(nome);
			squadre.setAllenatore(allenatore);
			squadre.setIndirizzo(indirizzo);
			squadre.setTelefono(telefono);
			squadre.setSito(sito);
			squadre.setJersey((Jersey) jersey);
			squadre.setGiocate(giocate);
			squadre.setDifferenza(differenza);
			squadre.setPareggi(pareggi);
			squadre.setSconfitte(sconfitte);
			squadre.setVittorie(vittorie);
			squadre.setMeteFatti(meteFatti);
			squadre.setMeteSubiti(meteSubiti);
			squadre.setPuntiFatti(puntiFatti);
			squadre.setPuntiSubiti(puntiSubiti);
			squadre.setPunti(punti);
			squadre.setGirone(girone);
//			
			
			
			
		return squadre;
		
//			
			
		}

	
private static Partite createPartita(String[] metadata) { 
			
			List<Squadre> squadre = readSquadreFromCSV("squadre3.csv"); 
			
			Long id = new Long(metadata[0]);
			LocalDate date = LocalDate.parse(metadata[1]);
			
			Integer girone = new Integer(metadata[8]);
			Squadre squadra1 =new Squadre();
			 for (Squadre s : squadre) { 
					if (s.getId().equals(new Long(metadata[2]))) {
						squadra1 = s;
					}
					}
			
			 Integer score1  =  new Integer(metadata[3]);
			 Integer mete1 = new Integer(metadata[4]);
			 
			 Squadre squadra2 =new Squadre();
			 for (Squadre s : squadre) { 
					if (s.getId().equals(new Long(metadata[7]))) {
						squadra2 = s;
					}
					}
			 
			 Integer score2  =  new Integer(metadata[5]);
			 Integer mete2   = new Integer(metadata[6]);
			 
			
				
			Partite partite  = new Partite();
			partite.setId(id);
			partite.setDate(date);
			partite.setSquadra1((Squadre)squadra1);
			partite.setPuntisquadra1(score1);
			partite.setMeteSquadra1(mete1);
			partite.setSquadra2((Squadre)squadra2);
			partite.setPuntisquadra2(score2);
			partite.setMeteSquadra2(mete2);
			partite.setGirone(girone);
			
//			
		return partite;
		
//			
			
		}
//		
//		for (String color : colors) {
//			  
//			
//			Jersey jersey  = new Jersey();
//			jersey.setColor(color);
//			jerseyRepository.save(jersey);
//			
//			
		
//			
//			}
		
	
		
		
	
}
