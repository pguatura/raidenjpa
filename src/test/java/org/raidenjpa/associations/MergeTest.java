package org.raidenjpa.associations;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.Test;
import org.raidenjpa.util.EntityManagerUtil;

public class MergeTest {
	
	@Test
	public void testSimpleTx() {
		A a = new A("a");
		B b = new B("b");
		C c = new C("c");
		
		EntityManager em = EntityManagerUtil.em();
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		a = em.merge(a);
		b = em.merge(b);
		c = em.merge(c);
		
		a.setB(b);
		b.setC(c);
		
		tx.commit();
		em.close();
		
		em = EntityManagerUtil.em();
		a = em.find(A.class, a.getId());
		assertEquals(a.getValue(), "a");
		assertEquals(a.getB().getValue(), "b");
		assertEquals(a.getB().getC().getValue(), "c");
		em.close();
	}
}
