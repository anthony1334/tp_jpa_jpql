package fr.diginamic;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.persistence.*;

import org.junit.Test;

public class ActeurRepositoryTest {
	
	/* EntityManagerFactory emf = Persistence.createEntityManagerFactory("movie_db");
	 EntityManager em = emf.createEntityManager();
     EntityTransaction et = em.getTransaction();*/

	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("movie_db");
	 private EntityManager em = emf.createEntityManager();
	EntityTransaction et = em.getTransaction();





	
	/**
	 * Extraire tous les acteurs triés dans l'ordre alphabétique des identités
	 */
	@Test
	public void testExtraireActeursTriesParIdentite() {
		et.begin();


		
		TypedQuery<Acteur> query = em.createQuery("SELECT a FROM Acteur a ORDER BY a.identite ", Acteur.class);
		List<Acteur> acteurs = query.getResultList();
		
		assertEquals(1137, acteurs.size());
		assertEquals("A.J. Danna", acteurs.get(0).getIdentite());

		et.commit();

		em.close();

	}
	
	/**
	 * Extraire l'actrice appelée Marion Cotillard
	 */
	@Test
	public void testExtraireActeursParIdentite() {
		et.begin();
		TypedQuery<Acteur> query = em.createQuery("SELECT a FROM Acteur a WHERE a.identite= 'Marion Cotillard'", Acteur.class);
		List<Acteur> acteurs = query.getResultList();
		
		assertEquals(1, acteurs.size());
		assertEquals("Marion Cotillard", acteurs.get(0).getIdentite());

		et.commit();
		em.close();
	}
	
	/**
	 * Extraire la liste des acteurs dont l'année de naissance est 1985.
	 * Astuce: fonction year(...)
	 */
	@Test
	public void testExtraireActeursParAnneeNaissance() {
		TypedQuery<Acteur> query = em.createQuery("SELECT a FROM Acteur a WHERE  YEAR (a.anniversaire) = 1985", Acteur.class);
		List<Acteur> acteurs = query.getResultList();
		
		assertEquals(10, acteurs.size());
	}
	
	/**
	 * Extraire la liste des actrices ayant joué le rôle d'Harley QUINN
	 */
	@Test
	public void testExtraireActeursParRole() {
		
		TypedQuery<Acteur> query = em.createQuery("SELECT a FROM Acteur a JOIN a.roles b WHERE b.nom ='Harley QUINN'", Acteur.class);
		List<Acteur> acteurs = query.getResultList();
		assertEquals("Margot Robbie", acteurs.get(0).getIdentite());
		assertEquals("Margot Robbie", acteurs.get(1).getIdentite());
	}
	
	/**
	 * Extraire la liste de tous les acteurs ayant joué dans un film paru en 2015.
	 */
	@Test
	public void testExtraireActeursParFilmParuAnnee() {
		TypedQuery<Acteur> query = em.createQuery("SELECT a FROM Acteur a JOIN a.roles b JOIN b.film  c WHERE  c.annee = 2015", Acteur.class);
		List<Acteur> acteurs = query.getResultList();
		assertEquals(140, acteurs.size());
	}
	
	/**
	 * Extraire la liste de tous les acteurs ayant joué dans un film français
	 * Astuce: mot clé distinct
	 */
	@Test
	public void testExtraireActeursParPays() {
		TypedQuery<Acteur> query = em.createQuery("SELECT DISTINCT a FROM Acteur a JOIN a.roles r JOIN r.film f JOIN f.pays p WHERE p.nom = 'FRANCE'", Acteur.class);
		List<Acteur> acteurs = query.getResultList();
		assertEquals(158, acteurs.size());
	}
	
	/**
	 * Extraire la liste de tous les acteurs ayant joué dans un film français paru en 2017
	 * Astuce: mot clé distinct
	 */
	@Test
	public void testExtraireActeursParListePaysEtAnnee() {
		TypedQuery<Acteur> query = em.createQuery("SELECT DISTINCT a FROM Acteur a JOIN a.roles r JOIN r.film f JOIN f.pays p WHERE p.nom = 'FRANCE' AND  f.annee = 2017", Acteur.class);
		List<Acteur> acteurs = query.getResultList();
		assertEquals(24, acteurs.size());
	}
	
	/**
	 * Extraire la liste de tous les acteurs ayant joué dans un film réalisé par Ridley Scott 
	 * entre les années 2010 et 2020
	 * Astuce: mot clé distinct
	 */
	@Test
	public void testExtraireParRealisateurEntreAnnee() {
		EntityTransaction et = em.getTransaction();
		et.begin();
		TypedQuery<Acteur> query = em.createQuery("SELECT DISTINCT a FROM Acteur a JOIN a.roles r JOIN r.film f JOIN f.realisateurs re WHERE re.identite = 'Ridley Scott '", Acteur.class);
		List<Acteur> acteurs = query.getResultList();
		assertEquals(27, acteurs.size());


	}


}