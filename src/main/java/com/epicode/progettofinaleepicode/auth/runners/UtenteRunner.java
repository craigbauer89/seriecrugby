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

import javax.persistence.EntityNotFoundException;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

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
import com.epicode.progettofinaleepicode.entity.Channel;
import com.epicode.progettofinaleepicode.entity.Classifica;
import com.epicode.progettofinaleepicode.entity.Jersey;
import com.epicode.progettofinaleepicode.entity.LoadIds;
import com.epicode.progettofinaleepicode.entity.News;
import com.epicode.progettofinaleepicode.entity.Participation;
import com.epicode.progettofinaleepicode.entity.Partite;
import com.epicode.progettofinaleepicode.entity.Picture;
import com.epicode.progettofinaleepicode.entity.Player;
import com.epicode.progettofinaleepicode.entity.Season;
import com.epicode.progettofinaleepicode.entity.Squadre;
import com.epicode.progettofinaleepicode.entity.Stadium;
import com.epicode.progettofinaleepicode.repository.JerseyRepository;
import com.epicode.progettofinaleepicode.repository.LoadIdsRepository;
import com.epicode.progettofinaleepicode.repository.NewsRepository;
import com.epicode.progettofinaleepicode.repository.ParticipationRepository;
import com.epicode.progettofinaleepicode.repository.PartiteRepository;
import com.epicode.progettofinaleepicode.repository.PictureRepository;
import com.epicode.progettofinaleepicode.repository.PlayerRepository;
import com.epicode.progettofinaleepicode.repository.SquadreRepository;
import com.epicode.progettofinaleepicode.repository.StadiumRepository;
import com.epicode.progettofinaleepicode.service.LoadIdsService;
import com.epicode.progettofinaleepicode.service.SquadreService;
import com.epicode.progettofinaleepicode.repository.ChampionshipRepository;
import com.epicode.progettofinaleepicode.repository.ChannelRepository;
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
	StadiumRepository stadiumRepository;
	ChannelRepository channelRepository;
	NewsRepository newsRepository;
	PlayerRepository playerRepository;
	PictureRepository pictureRepository;
	ParticipationRepository participationRepository;
	LoadIdsRepository loadIdsReposotory;
	LoadIdsService loadIdsService;
	

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
		
		List<Picture> picture = readPictureFromCSV("picture.csv"); 
		for (Picture p : picture) { 
			System.out.println(p);
			pictureRepository.save(p);
			}
		
		List<Squadre> squadre = readSquadreFromCSV("squadre3.csv"); 
		List<Channel> channels = readChannelFromCSV("channels.csv");
		for (Channel channel: channels) {
			channelRepository.save(channel);
		}
	
		LoadIds loadId= new LoadIds();
		loadIdsReposotory.save(loadId);
		
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
						List<Participation> classificaParitipazione = new ArrayList<Participation>();
						for (Squadre squadra : squadre) {
							   if (squadra.getParticipation() == null || squadra.getParticipation().isEmpty()) {
							        Participation participation = new Participation();
							        
							        participation.setGiocate(squadra.getGiocate());
							        participation.setVittorie(squadra.getVittorie());
							        participation.setPareggi(squadra.getPareggi());
							        participation.setSconfitte(squadra.getSconfitte());
							        participation.setMeteFatti(squadra.getMeteFatti());
							        participation.setMeteSubiti(squadra.getMeteSubiti());
							        participation.setPuntiSubiti(squadra.getPuntiSubiti());
							        participation.setPuntiFatti(squadra.getPuntiFatti());
							        participation.setDifferenza(squadra.getDifferenza());
							        participation.setPuntiBonus(squadra.getPuntiBonus());
							        participation.setPunti(squadra.getPunti());
							        
							        participation.setSquadra(squadra);
							        squadra.setParticipation(List.of(participation));
							    }
								switch (squadra.getGirone()) {
							    case 1:
							    	 if (item2.equals("Group A")) {
							    		 classificaSquadre.add(squadra);
							    		 if (squadra.getClassifica() == null) {
							    			    squadra.setClassifica(new ArrayList<>());
							    			}
							    			squadra.getClassifica().add(classifica);
							    			if (squadra.getParticipation() == null) {
								    		    squadra.setParticipation(new ArrayList<>());
								    		}
//							    			 if (squadra.getParticipation().isEmpty()) {
//							                     Participation participation = new Participation();
//							                     participation.setSquadra(squadra);
//							                     squadra.getParticipation().add(participation);
//							                     classificaParitipazione.add(squadra.getParticipation().get(0));
//							                 }
							    			
							    			
							    		//	Participation participation = new Participation();
							    		//	participation.setSquadra(squadra);
								    	//	participation.setClassifica(classifica);
							    		//	List<Participation> participations = new ArrayList<Participation>();
							    		//	participations.add(participation);						    			
							    			//classifica.aggiungiSquadra(squadra);
							    			//squadra.setParticipation(participations);
							    		//	squadra.getParticipation().add(participation);
							    		//	classificaParitipazione.add(squadra.getParticipation().get(0));

							    			
					                    }
							        break;
							    case 2:
							    	 if (item2.equals("Group B")) {
							    		 classificaSquadre.add(squadra);
							    		 if (squadra.getClassifica() == null) {
							    			    squadra.setClassifica(new ArrayList<>());
							    			}
							    			squadra.getClassifica().add(classifica);
							    			if (squadra.getParticipation() == null) {
								    		    squadra.setParticipation(new ArrayList<>());
								    		}
//							    			 if (squadra.getParticipation().isEmpty()) {
//							                     Participation participation = new Participation();
//							                     participation.setSquadra(squadra);
//							                     squadra.getParticipation().add(participation);
//							                     classificaParitipazione.add(squadra.getParticipation().get(0));
//							                 }
//							    			
							    			
							    		//	Participation participation = new Participation();
							    		//	participation.setSquadra(squadra);
								    	//	participation.setClassifica(classifica);
							    		//	List<Participation> participations = new ArrayList<Participation>();
							    		//	participations.add(participation);						    			
							    			//classifica.aggiungiSquadra(squadra);
							    			//squadra.setParticipation(participations);
							    		//	squadra.getParticipation().add(participation);
							    		//	classificaParitipazione.add(squadra.getParticipation().get(0));
					               
					                    }
							        break;
							    case 3:
							    	 if (item2.equals("Group C")) {
							    		 classificaSquadre.add(squadra);
							    		 if (squadra.getClassifica() == null) {
							    			    squadra.setClassifica(new ArrayList<>());
							    			}
							    			squadra.getClassifica().add(classifica);	
							    			if (squadra.getParticipation() == null) {
								    		    squadra.setParticipation(new ArrayList<>());
								    		}
//							    			 if (squadra.getParticipation().isEmpty()) {
//							                     Participation participation = new Participation();
//							                     participation.setSquadra(squadra);
//							                     squadra.getParticipation().add(participation);
//							                     classificaParitipazione.add(squadra.getParticipation().get(0));
//							                 }
							    			
							    			
							    		//	Participation participation = new Participation();
							    		//	participation.setSquadra(squadra);
								    	//	participation.setClassifica(classifica);
							    		//	List<Participation> participations = new ArrayList<Participation>();
							    		//	participations.add(participation);						    			
							    			//classifica.aggiungiSquadra(squadra);
							    			//squadra.setParticipation(participations);
							    		//	squadra.getParticipation().add(participation);
							    		//	classificaParitipazione.add(squadra.getParticipation().get(0));
					                    }
							        break;
							    case 4:
							    	 if (item2.equals("Group D")) {
							    		 classificaSquadre.add(squadra);
							    		 if (squadra.getClassifica() == null) {
							    			    squadra.setClassifica(new ArrayList<>());
							    			}
							    			squadra.getClassifica().add(classifica);
							    			if (squadra.getParticipation() == null) {
								    		    squadra.setParticipation(new ArrayList<>());
								    		}
//							    			 if (squadra.getParticipation().isEmpty()) {
//							                     Participation participation = new Participation();
//							                     participation.setSquadra(squadra);
//							                     squadra.getParticipation().add(participation);
//							                     classificaParitipazione.add(squadra.getParticipation().get(0));
//							                 }
							    			
							    			
							    		//	Participation participation = new Participation();
							    		//	participation.setSquadra(squadra);
								    	//	participation.setClassifica(classifica);
							    		//	List<Participation> participations = new ArrayList<Participation>();
							    		//	participations.add(participation);						    			
							    			//classifica.aggiungiSquadra(squadra);
							    			//squadra.setParticipation(participations);
							    		//	squadra.getParticipation().add(participation);
							    		//	classificaParitipazione.add(squadra.getParticipation().get(0));
					                    }
							        break;
							    case 5:
							    	 if (item2.equals("Group E")) {
							    		 classificaSquadre.add(squadra);
							    		 if (squadra.getClassifica() == null) {
							    			    squadra.setClassifica(new ArrayList<>());
							    			}
							    			squadra.getClassifica().add(classifica);
							    			if (squadra.getParticipation() == null) {
								    		    squadra.setParticipation(new ArrayList<>());
								    		}
//							    			 if (squadra.getParticipation().isEmpty()) {
//							                     Participation participation = new Participation();
//							                     participation.setSquadra(squadra);
//							                     squadra.getParticipation().add(participation);
//							                     classificaParitipazione.add(squadra.getParticipation().get(0));
//							                 }
							    			
							    			
							    		//	Participation participation = new Participation();
							    		//	participation.setSquadra(squadra);
								    	//	participation.setClassifica(classifica);
							    		//	List<Participation> participations = new ArrayList<Participation>();
							    		//	participations.add(participation);						    			
							    			//classifica.aggiungiSquadra(squadra);
							    			//squadra.setParticipation(participations);
							    		//	squadra.getParticipation().add(participation);
							    		//	classificaParitipazione.add(squadra.getParticipation().get(0));
					                    }
							        break;
							    case 6:
							    	 if (item2.equals("Group F")) {
							    		 classificaSquadre.add(squadra);
							    		 if (squadra.getClassifica() == null) {
							    			    squadra.setClassifica(new ArrayList<>());
							    			}
							    		squadra.getClassifica().add(classifica);
							    		if (squadra.getParticipation() == null) {
							    		    squadra.setParticipation(new ArrayList<>());
							    		}
//							 			 if (squadra.getParticipation().isEmpty()) {
//						                     Participation participation = new Participation();
//						                     participation.setSquadra(squadra);
//						                     squadra.getParticipation().add(participation);
//						                     classificaParitipazione.add(squadra.getParticipation().get(0));
//						                 }
						    			
						    			
						    		//	Participation participation = new Participation();
						    		//	participation.setSquadra(squadra);
							    	//	participation.setClassifica(classifica);
						    		//	List<Participation> participations = new ArrayList<Participation>();
						    		//	participations.add(participation);						    			
						    			//classifica.aggiungiSquadra(squadra);
						    			//squadra.setParticipation(participations);
						    		//	squadra.getParticipation().add(participation);
						    		//	classificaParitipazione.add(squadra.getParticipation().get(0));
					                    }
							        break;
							    default:
							        System.out.println("Altro numero");
							        break;
								}
								
								squadraRepository.save(squadra);
												
						}
						
					
						classifica.setSquadre(classificaSquadre);
						classifica.setParticipation(classificaParitipazione);					
						List<Channel> repochannels = channelRepository.findAll();
						List<Partite> partite = readPartiteFromCSV("partite.csv",squadre,repochannels); 
						
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
		List<Squadre> repoSquadre  = squadraRepository.findAll();
		List<Classifica> repoClassifica  = classificaRepository.findAll();
		
		
		
		for (Squadre squadra : squadraRepository.findAllWithParticipation()) {
			
			Long participationID = squadra.getParticipation().get(0).getId();

		    // Prende tutte le classifiche in cui è presente questa squadra
		    List<Classifica> classifiche = classificaRepository.findClassificheBySquadraId(squadra.getId());

		    for (Classifica classifica : classifiche) {

		        // Verifica se esiste già una participation per questa combinazione
		        boolean exists = participationRepository.existsBySquadraIdAndClassificaId(
		            squadra.getId(), classifica.getId());

		        if (exists) continue; // Esiste già → non fare nulla
		        
		   
		        Participation p = participationRepository
		        	    .findById(participationID).orElseThrow(() -> new RuntimeException("Participation not found"));

		        p.setClassifica(classifica);
		        
		      //  p.setSquadra(squadra);
		        participationRepository.save(p);

		        System.out.println("✅ Participation creata: squadra ID " + squadra.getId() +
		                           ", classifica ID " + classifica.getId());
		    }
		}


			
		

		
		
		
		
//		for (Classifica classifica : repoClassifica) {
//		
//			Long classificaId = classifica.getId();
//			
//			for (Squadre squadra : repoSquadre) {
//				
//				List<Classifica> squadreClassifiche  = squadra.getClassifica();
//				
//				for (Classifica sc: squadreClassifiche) {
//					Long squadraClassificaId = sc.getId();
//					
//					if (classificaId.equals(squadraClassificaId)) {
//						
//						List<Participation>  participations = squadra.getPartecipition();
//						//List<Participation> participations = new ArrayList<Participation>();
//						Participation participation = participations.get(0);		
//						participation.setClassifica(classifica);
//						classifica.setPartecipition(participations);	
//						participationRepository.save(participation);
//						
//					}
//				}
//			}
//			classificaRepository.save(classifica);
//		}
		
		List<Stadium> stadium = readStadiumFromCSV("stadium.csv", squadraRepository, pictureRepository); 
		for (Stadium s : stadium) { 
			System.out.println(s);
			stadiumRepository.save(s);
			Squadre squadra = s.getSquadre().get(0);
			squadra.setStadium(s);	
			//	List<Squadre> squadralist = new ArrayList<>();
		//	squadralist.add(squadra);		
		//	s.setSquadre(squadralist);
			squadraRepository.save(squadra);
			}	
		
		List<News> news = readNewsFromCSV("news.csv", pictureRepository); 
		for (News s : news) { 
			System.out.println(s);
			newsRepository.save(s);
			}
		


		List<Player> players = readPlayersFromCSV("players.csv",repoSquadre, pictureRepository); 
		for (Player s : players) { 
			System.out.println(s);
			playerRepository.save(s);
			}
		
		
		
		Long id = (long) 1;
		//java.util.Optional<Squadre> testSquadra = squadraService.getById(id);
		//List<Classifica> testClassifca = testSquadra.get().getClassifica();
		//need to get the above the opposite way, throguh classica, not through squadra  - as will show alzy load , session closed error
		
		String anno = "2022";
		List<Classifica> testClassifca = classificaRepository.findBySquadraIdAndSeasonYear(id, anno);
	//	System.out.println(testClassifca);
		
		
		// if the below is somethign you want to provide, need to revisit
				
		//List<Partite> testHomePartite = testSquadra.get().getAwaygames();
		//List<Partite> testAwayPartite = testSquadra.get().getAwaygames();
		
		//System.out.println("HOMEGAMES"+ testHomePartite);
		//System.out.println("AWAYGAMES"+ testAwayPartite);
		
	}
			 
	private static List<Partite> readPartiteFromCSV(String fileName,List<Squadre> squadre,List<Channel> channels  ) { 
		List<Partite> partite = new ArrayList<>();
		Path pathToFile = Paths.get(fileName); 
		
		try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {
			
			String line = br.readLine();
			
			while (line != null) { 
				String[] attributes = line.split(","); 
				Partite partita = createPartita(attributes,squadre,channels); 
				partite.add(partita); // read next line before looping // if end of file reached, line would be null 
				line = br.readLine(); 
				} 
			} catch (IOException ioe) { 
				ioe.printStackTrace();
			} 
		return partite; 
		
		} 
	
	private static List<News> readNewsFromCSV(String fileName, PictureRepository pictureRepository ) { 
		List<News> news = new ArrayList<>();
		Path pathToFile = Paths.get(fileName); 
		
		try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {
			
			String line = br.readLine();
			
			while (line != null) { 
				String[] attributes = line.split(","); 
				News news1 = createNews(attributes,pictureRepository); 
				news.add(news1); // read next line before looping // if end of file reached, line would be null 
				line = br.readLine(); 
				} 
			} catch (IOException ioe) { 
				ioe.printStackTrace();
			} 
		return news; 
		
	} 
	
	private static List<Picture> readPictureFromCSV(String fileName ) { 
		List<Picture> picture = new ArrayList<>();
		Path pathToFile = Paths.get(fileName); 
		
		try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {
			
			String line = br.readLine();
			
			while (line != null) { 
				String[] attributes = line.split(","); 
				Picture picture1 = createPicture(attributes); 
				picture.add(picture1); // read next line before looping // if end of file reached, line would be null 
				line = br.readLine(); 
				} 
			} catch (IOException ioe) { 
				ioe.printStackTrace();
			} 
		return picture; 
		
	} 
	
	private static List<Channel> readChannelFromCSV(String fileName) { 
		List<Channel> channels = new ArrayList<>();
		Path pathToFile = Paths.get(fileName); 
		
		try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {
			
			String line = br.readLine();
			
			while (line != null) { 
				String[] attributes = line.split(","); 
				Channel channel = createChannel(attributes); 
				channels.add(channel); // read next line before looping // if end of file reached, line would be null 
				line = br.readLine(); 
				} 
			} catch (IOException ioe) { 
				ioe.printStackTrace();
			} 
		return channels; 
		
	} 
	
	private static List<Player> readPlayersFromCSV(String fileName,List<Squadre> squadre,PictureRepository pictureRepository  ) { 
		List<Player> players = new ArrayList<>();
		Path pathToFile = Paths.get(fileName); 
		
		try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {
			
			String line = br.readLine();
			
			while (line != null) { 
				String[] attributes = line.split(","); 
				Player player = createPlayer(attributes, squadre, pictureRepository); 
				players.add(player); // read next line before looping // if end of file reached, line would be null 
				line = br.readLine(); 
				} 
			} catch (IOException ioe) { 
				ioe.printStackTrace();
			} 
		return players; 
		
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
		
		private static List<Stadium> readStadiumFromCSV(String fileName,SquadreRepository squadraRepository, PictureRepository pictureRepository ) { 

		//private static List<Stadium> readStadiumFromCSV(String fileName,List<Squadre> squadre, PictureRepository pictureRepository ) { 
			List<Stadium> stadiums = new ArrayList<>();
			Path pathToFile = Paths.get(fileName); 
			
			try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {
				
				String line = br.readLine();
				
				while (line != null) { 
					String[] attributes = line.split(","); 
					Stadium stadium = createStadium(attributes, squadraRepository, pictureRepository); 
					stadiums.add(stadium); // read next line before looping // if end of file reached, line would be null 
					line = br.readLine(); 
					} 
				} catch (IOException ioe) { 
					ioe.printStackTrace();
				} 
			return stadiums; 
			
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
		
private static Stadium createStadium(String[] metadata, SquadreRepository squadraRepository, PictureRepository pictureRepository) { 
// private static Stadium createStadium(String[] metadata, List<Squadre> squadre, PictureRepository pictureRepository) { 

	
			Long id = new Long(metadata[0]);
			BigDecimal latitude = new BigDecimal(metadata[1]);
			BigDecimal longitude =  new BigDecimal(metadata[2]);
			 String nome  =  metadata[3];
			 String allenatore = metadata[4];
			 String indirizzo  = metadata[5];
			 String sito  =  metadata[6];
			 String telefono   =  metadata[7];
//			 Squadre squadra =new Squadre();
//				 for (Squadre s : squadre) { 
//						if (s.getId().equals(new Long(metadata[9]))) {
//							squadra = s;
//						}
//					}
			 
			Long squadraId = new Long(metadata[9]);
			Squadre squadra = squadraRepository.findById(squadraId).orElse(null);
			
			Long pictureId = new Long(metadata[8]);
			Picture picture = pictureRepository.findById(pictureId).orElse(null);
			 
				
			Stadium stadium  = new Stadium();
			stadium.setLatitude(latitude);
			stadium.setLongitude(longitude);
			stadium.setName(nome);
			stadium.setAllenatore(allenatore);
			stadium.setIndirizzo(indirizzo);
			stadium.setTelefono(telefono);
			stadium.setSito(sito);
			stadium.setPicture(picture);	
			List<Squadre> squadralist = new ArrayList<>();
			squadralist.add(squadra);	
			stadium.setSquadre(squadralist);
			
			return stadium;
		
		}
		
		private static Jersey createJersey(Long id, String colour) { 
			
			List<Squadre> squadre = new ArrayList<Squadre>();
		
			Jersey jersey = new Jersey();
			
			jersey.setColor(colour);
			jersey.setId(id);
			jersey.setSquadre(squadre);
			
		return jersey;
			
		}
		
		private static Picture createPicture(String[] metadata) { 
			
			String name = metadata[1];

			List<News> news = new ArrayList<News>();
			List<Player> player = new ArrayList<Player>();
			List<Stadium> stadium = new ArrayList<Stadium>();
		
			Picture picture = new Picture();
			
			picture.setName(name);
//			picture.setNews(news);
//			picture.setPlayer(player);
//			picture.setStadium(stadium);
			
		return picture;
			
		}
		

	
		private static Partite createPartita(String[] metadata,List<Squadre> squadre, List<Channel> channels) { 
			
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
			 
			 Channel channel = null; // null until found

			 if (metadata[9] != null && metadata[9].matches("\\d+")) { // only digits
			     Long channelId = Long.parseLong(metadata[9]);
			     for (Channel c : channels) {
			         if (c.getId().equals(channelId)) {
			             channel = c;
			             break;
			         }
			     }
			 } else {
			     System.err.println("Invalid channel ID in CSV (expected number, got: '" + metadata[9] + "')");
	
			 }
			 
			 Integer score2  =  new Integer(metadata[5]);
			 Integer mete2   = new Integer(metadata[6]);
			 String tickets = squadra1.getAllenatore();
			
			 
			
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
			partite.setTickets(tickets);
			partite.setChannel(channel);		
			partite.setPlayed(true);
			return partite;
				
		
		
			
		}
		
		private static Channel createChannel(String[] metadata) { 
			
			Channel channel  = new Channel();
			//Long id = new Long(metadata[0]);
			//channel.setId(id);
			channel.setCountry(metadata[1]);
			channel.setName(metadata[2]);
			channel.setFree(metadata[3].equals("Yes"));

			return channel;
				
		}
	
	private static News createNews(String[] metadata,PictureRepository pictureRepository) { 
			
		

			String title = metadata[1];
			String content = metadata[2];
		
			News news  = new News();
			
			Long pictureId = new Long(metadata[3]);
			Picture picture = pictureRepository.findById(pictureId).orElse(null);

			
			news.setContent(content);	
			news.setPicture(picture);	
			news.setTitle(title);			
			return news;
				
			
		}


	
	private static Player createPlayer(String[] metadata,List<Squadre> squadre,PictureRepository pictureRepository) { 
		
		String name = metadata[1];
		Integer tries =  new Integer(metadata[2]);
		Integer gialli =  new Integer(metadata[3]);
		Integer rossi =  new Integer(metadata[4]);
		Integer punti =  new Integer(metadata[5]);

		Squadre squadra1 =new Squadre();
		 for (Squadre s : squadre) { 
				if (s.getId().equals(new Long(metadata[6]))) {
					squadra1 = s;
				}
				}
		
		 Long pictureId = new Long(metadata[7]);
			Picture picture = pictureRepository.findById(pictureId).orElse(null);
		
		 Player player  = new Player();
		 player.setName(name);
		 player.setTries(tries);	
		 player.setGialli(gialli);
		 player.setRossi(rossi);
		 player.setPunti(punti);
		 player.setSquadra((Squadre)squadra1);
		 player.setPicture(picture);
		 
		 
	return player;
			
		
	}
		
	
}
