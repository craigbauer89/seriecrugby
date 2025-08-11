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
import com.epicode.progettofinaleepicode.entity.Championship;
import com.epicode.progettofinaleepicode.entity.Classifica;
import com.epicode.progettofinaleepicode.entity.Jersey;
import com.epicode.progettofinaleepicode.entity.Partite;
import com.epicode.progettofinaleepicode.entity.Season;
import com.epicode.progettofinaleepicode.entity.Squadre;
import com.epicode.progettofinaleepicode.repository.JerseyRepository;
import com.epicode.progettofinaleepicode.repository.PartiteRepository;
import com.epicode.progettofinaleepicode.repository.SquadreRepository;
import com.epicode.progettofinaleepicode.service.SquadreService;
import com.epicode.progettofinaleepicode.repository.ChampionshipRepository;
import com.epicode.progettofinaleepicode.repository.SeasonRepository;
import com.epicode.progettofinaleepicode.repository.ClassificaRepository;

import lombok.AllArgsConstructor;
import net.bytebuddy.dynamic.DynamicType.Builder.FieldDefinition.Optional;


@AllArgsConstructor
@Component
public class UtenteRunner implements ApplicationRunner {


	JerseyRepository jerseyRepository;
	SeasonRepository seasonRepository;
	ChampionshipRepository championshipRepository;
	ClassificaRepository classificaRepository;
	SquadreRepository squadraRepository;
	PartiteRepository partiteRepository;
	RoleRepository roleRepository;
	UserRepository userRepository;
	PasswordEncoder encoder;
	SquadreService squadraService;
	

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
		userAdmin.setRoles(ruoliAdministrator);
		userRepository.save(userAdmin);
		
		Set<Role> ruoliUtente = new HashSet<Role>();
		ruoliUtente.add(user);
		
		Utente simpleUser = new Utente();
		simpleUser.setUsername("user1");
		simpleUser.setPassword( encoder.encode("qwerty"));
		simpleUser.setRoles(ruoliUtente);
		userRepository.save(simpleUser);
		
		
		String[] colors = {"red", "white", "purple", "wine","bluedarkblue", "black", "blueyellow", "redorange", "blackgreen",
				"redblue", "redwhite", "whiteblue", "yellowgreen","whitedarkblue", "greenblue", "redblackyellow", "greenyellowblack","blueredwhite" , "blackgreen" , "blackyellowwhite" ,"blackwhite", "extracolor"};
		
		for (String color : colors) {
			  
		
		Jersey jersey  = new Jersey();
		jersey.setColor(color);
		jerseyRepository.save(jersey);
		
		
		}
		
		List<Squadre> squadre = readSquadreFromCSV("squadre3.csv"); 

		
		String[] seasonslist = {"2021","2020","2023","2025","2019","2018","2022","2024" };
		String[] classificalist = {"Group A","Group D","Group C","Group B","Group F","Group E" };
		String[] champsionshiplist = {"Serie A","Serie D","Serie E","Serie F","Serie C","Serie B" };
		
		List<Season> seasons = new ArrayList<Season>();
		for (String item : seasonslist) {
			Season season = new Season();
			season.setYear(item);
			seasons.add(season);
			
		
			List<Championship> championships = new ArrayList<Championship>();
			for (String item1 : champsionshiplist) {
				Championship championship = new Championship();	
				championship.setName(item1);	
				championship.setSeason(season);
				
				List<Classifica> classifice = new ArrayList<Classifica>();
				for (String item2 : classificalist) {
					Classifica classifica = new Classifica();
					classifica.setName(item2);
					classifica.setChampionship(championship);
					if (item.equals("2022") && item1.equals("Serie C") ) {
						List<Squadre> classificaSquadre = new ArrayList<Squadre>();
						for (Squadre squadra : squadre) {
							
								switch (squadra.getGirone()) {
							    case 1:
							    	 if (item2.equals("Group A")) {
							    		 classificaSquadre.add(squadra);
							    		 if (squadra.getClassifica() == null) {
							    			    squadra.setClassifica(new ArrayList<>());
							    			}
							    			squadra.getClassifica().add(classifica);
					                    }
							        break;
							    case 2:
							    	 if (item2.equals("Group B")) {
							    		 classificaSquadre.add(squadra);
							    		 if (squadra.getClassifica() == null) {
							    			    squadra.setClassifica(new ArrayList<>());
							    			}
							    			squadra.getClassifica().add(classifica);
					               
					                    }
							        break;
							    case 3:
							    	 if (item2.equals("Group C")) {
							    		 classificaSquadre.add(squadra);
							    		 if (squadra.getClassifica() == null) {
							    			    squadra.setClassifica(new ArrayList<>());
							    			}
							    			squadra.getClassifica().add(classifica);
					                    }
							        break;
							    case 4:
							    	 if (item2.equals("Group D")) {
							    		 classificaSquadre.add(squadra);
							    		 if (squadra.getClassifica() == null) {
							    			    squadra.setClassifica(new ArrayList<>());
							    			}
							    			squadra.getClassifica().add(classifica);
					                    }
							        break;
							    case 5:
							    	 if (item2.equals("Group E")) {
							    		 classificaSquadre.add(squadra);
							    		 if (squadra.getClassifica() == null) {
							    			    squadra.setClassifica(new ArrayList<>());
							    			}
							    			squadra.getClassifica().add(classifica);
					                    }
							        break;
							    case 6:
							    	 if (item2.equals("Group F")) {
							    		 classificaSquadre.add(squadra);
							    		 if (squadra.getClassifica() == null) {
							    			    squadra.setClassifica(new ArrayList<>());
							    			}
							    			squadra.getClassifica().add(classifica);
					                    }
							        break;
							    default:
							        System.out.println("Altro numero");
							        break;
								}
								
								squadraRepository.save(squadra);
								
						}
						classifica.setSquadre(classificaSquadre);
						List<Partite> partite = readPartiteFromCSV("partite.csv",squadre); 
						List<Partite> classificaPartite = new ArrayList<Partite>();
						for (Partite p : partite) { 
							System.out.println(p);
							//ISSUE this will always be null based on how we set up the class
							Long girone = p.getClassifica_id();
							
								switch (girone.intValue()) {
							    case 1:
							    	 if (item2.equals("Group A")) {
							    		 classificaPartite.add(p);
							    		 p.setClassifica(classifica);
					                    }
							        break;
							    case 2:
							    	 if (item2.equals("Group B")) {
							    		 classificaPartite.add(p);
							    		 p.setClassifica(classifica);
					                    }
							        break;
							    case 3:
							    	 if (item2.equals("Group C")) {
							    		 classificaPartite.add(p);
							    		 p.setClassifica(classifica);
					                    }
							        break;
							    case 4:
							    	 if (item2.equals("Group D")) {
							    		 classificaPartite.add(p);
							    		 p.setClassifica(classifica);
					                    }
							        break;
							    case 5:
							    	 if (item2.equals("Group E")) {
							    		 classificaPartite.add(p);
							    		 p.setClassifica(classifica);
					                    }
							        break;
							    case 6:
							    	 if (item2.equals("Group F")) {
							    		 classificaPartite.add(p);
							    		 p.setClassifica(classifica);
					                    }
							        break;
							    default:
							        System.out.println("Altro numero");
							        break;
								}
								
							//	partiteRepository.save(p);
								
						}
						classifica.setPartite(classificaPartite);
					}
					classifice.add(classifica);
				}
				championship.setClassifica(classifice);
				championships.add(championship);
		//		championshipRepository.save(championship);
			}
			season.setLeague(championships);
			seasonRepository.save(season);
		}
	//	for (Season item : seasons) {
	//		item.setLeague(championships);
	//	}
		
//		List<Partite> partite = readPartiteFromCSV("partite.csv",squadre); 
//		for (Partite p : partite) { 
//			System.out.println(p);
//			partiteRepository.save(p);
//			}	
		
		//TESTING//
		
		Long id = (long) 1;
		java.util.Optional<Squadre> testSquadra = squadraService.getById(id);
		List<Classifica> testClassifca = testSquadra.get().getClassifica();
		System.out.println(testClassifca);
		
		
		List<Partite> testHomePartite = testSquadra.get().getAwaygames();
		List<Partite> testAwayPartite = testSquadra.get().getAwaygames();
		
		System.out.println("HOMEGAMES"+ testHomePartite);
		System.out.println("AWAYGAMES"+ testAwayPartite);
		
	}
			 
	private static List<Partite> readPartiteFromCSV(String fileName,List<Squadre> squadre ) { 
		List<Partite> partite = new ArrayList<>();
		Path pathToFile = Paths.get(fileName); 
		
		try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {
			
			String line = br.readLine();
			
			while (line != null) { 
				String[] attributes = line.split(","); 
				Partite partita = createPartita(attributes,squadre); 
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
			 
			 Jersey jersey  =  createJersey(new Long(metadata[8]), colors[ new Integer(metadata[8])] );
			 
				
			Squadre squadre  = new Squadre();
			//squadre.setId(id);
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
		
		return squadre;
		
		}
		
		private static Jersey createJersey(Long id, String colour) { 
			
			List<Squadre> squadre = new ArrayList<Squadre>();
		
			Jersey jersey = new Jersey();
			
			jersey.setColor(colour);
			jersey.setId(id);
			jersey.setSquadre(squadre);
			
		return jersey;
			
		}

	
		private static Partite createPartita(String[] metadata,List<Squadre> squadre) { 
			
//			List<Squadre> squadre = readSquadreFromCSV("squadre3.csv"); 
//			
//			Long id = new Long(metadata[0]);
			LocalDate date = LocalDate.parse(metadata[1]);
			
			Long girone = new Long(metadata[8]);
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
		//	partite.setId(id);
			partite.setDate(date);
			partite.setSquadra1((Squadre)squadra1);
			partite.setPuntisquadra1(score1);
			partite.setMeteSquadra1(mete1);
			partite.setSquadra2((Squadre)squadra2);
			partite.setPuntisquadra2(score2);
			partite.setMeteSquadra2(mete2);
			partite.setClassifica_id(girone);					
		return partite;
				
		
		
			
		}
	



	
		
		
	
}
