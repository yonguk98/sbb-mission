package com.mysite.sbb;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SbbApplicationTests {

	@Autowired
	private QuestionRepository questionRepository;
	@Autowired
	private AnswerRepository answerRepository;


	@BeforeEach
	void beforeEach() {
		Question q1 = Question.builder()
						.subject("sbb가 무엇인가요?")
						.content("sbb에 대해서 알고 싶습니다.")
						.createDate(LocalDateTime.now())
						.build();
		this.questionRepository.save(q1);  // 첫번째 질문 저장

		Question q2 = Question.builder()
						.subject("스프링부트 모델 질문입니다.")
						.content("id는 자동으로 생성되나요?")
						.createDate(LocalDateTime.now())
						.build();
		this.questionRepository.save(q2);

		Optional<Question> oq = this.questionRepository.findById(2);
		assertTrue(oq.isPresent());
		Question q = oq.get();

		Answer a = Answer.builder()
				.content("네 자동으로 생성됩니다.")
				.question(q)
				.createDate(LocalDateTime.now())
				.build();
		this.answerRepository.save(a);
	}

	@AfterEach
	void afterEach(){
		questionRepository.deleteAll();
		answerRepository.deleteAll();
	}

	@Test
	@DisplayName("2")
	void testJpa2() {
		List<Question> all = this.questionRepository.findAll();
		assertEquals(2, all.size());

		Question q = all.get(0);
		assertEquals("sbb가 무엇인가요?", q.getSubject());
	}

	@Test
	@DisplayName("3")
	void testJpa3() {
		Optional<Question> oq = this.questionRepository.findById(1);
		if (oq.isPresent()) {
			Question q = oq.get();
			assertEquals("sbb가 무엇인가요?", q.getSubject());
		}
	}

	@Test
	@DisplayName("4")
	void testJpa4() {
		Question q = this.questionRepository.findBySubject("sbb가 무엇인가요?");
		assertEquals(1, q.getId());
	}

	@Test
	@DisplayName("5")
	void testJpa5() {
		Question q = this.questionRepository.findBySubjectAndContent(
				"sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");
		assertEquals(1, q.getId());
	}

	@Test
	@DisplayName("6")
	void testJpa6() {
		List<Question> qList = this.questionRepository.findBySubjectLike("sbb%");
		Question q = qList.get(0);
		assertEquals("sbb가 무엇인가요?", q.getSubject());
	}

	@Test
	@DisplayName("7")
	void testJpa7() {
		Optional<Question> oq = this.questionRepository.findById(1);
		assertTrue(oq.isPresent());
		Question q = oq.get();
		q.toBuilder().subject("수정된 제목").build();
		this.questionRepository.save(q);
	}

	@Test
	@DisplayName("8")
	void testJpa8() {
		assertEquals(2, this.questionRepository.count());
		Optional<Question> oq = this.questionRepository.findById(1);
		assertTrue(oq.isPresent());
		Question q = oq.get();
		this.questionRepository.delete(q);
		assertEquals(1, this.questionRepository.count());
	}

	@Test
	@DisplayName("10")
	void testJpa10() {
		Optional<Answer> oa = this.answerRepository.findById(1);
		assertTrue(oa.isPresent());
		Answer a = oa.get();
		assertEquals(2, a.getQuestion().getId());
	}

	@Test
	@DisplayName("11")
	@Transactional
	void testJpa11() {
		Optional<Question> oq = this.questionRepository.findById(2);
		assertTrue(oq.isPresent());
		Question q = oq.get();

		List<Answer> answerList = q.getAnswerList();

		assertEquals(1, answerList.size());
		assertEquals("네 자동으로 생성됩니다.", answerList.get(0).getContent());
	}

}