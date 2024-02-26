package com.mysite.sbb;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SbbApplicationTests {

	@Autowired
	private QuestionRepository questionRepository;
	@Autowired
	private AnswerRepository answerRepository;

	/* 질문 데이터 생성 */
	@Test
	void testJpa() {
		Question q1 = new Question();
		q1.setSubject("sbb가 무엇인가요?");
		q1.setContent("sbb에 대해서 알려주세요.");
		q1.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q1);

		Question q2 = new Question();
		q2.setSubject("스프링 부트 모델 관련 질문입니다.");
		q2.setContent("id는 자동으로 생성되나요???");
		q2.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q2);

		Question q3 = new Question();
		q3.setSubject("가입인사 드립니다.");
		q3.setContent("안녕하세요 반갑습니다.");
		q3.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q3);
	}

	/* 질문 데이터 조회 */
	@Test
	void findAllTest() {
		List<Question> questionList = this.questionRepository.findAll();
		assertThat(questionList.size()).isEqualTo(3);
	}

	@Test
	void findByIdTest() {
		Optional<Question> op = this.questionRepository.findById(2);
		if (op.isPresent()) {
			Question q = op.get();
			assertThat(q.getSubject()).isEqualTo("스프링 부트 모델 관련 질문입니다.");
		}
	}

	@Test
	void findBySubjectTest() {
		Optional<Question> op = this.questionRepository.findBySubject("가입인사 드립니다.");
		if (op.isPresent()) {
			Question question = op.get();
			assertThat(question.getId()).isEqualTo(3);
		}
	}

	@Test
	void findBySubjectLikeTest() {
		List<Question> questionList = this.questionRepository.findBySubjectLike("%니다.");
		assertThat(questionList.size()).isEqualTo(2);
	}

	/* 질문 데이터 수정 */
	@Test
	void modifyTest() {
		Optional<Question> op = this.questionRepository.findById(1);
		assertTrue(op.isPresent());
		Question question = op.get();
		question.setSubject("수정된 제목");
		this.questionRepository.save(question);
	}

	/* 질문 데이터 삭제 */
	@Test
	void deleteTest() {
		Optional<Question> op = this.questionRepository.findById(1);
		assertTrue(op.isPresent());
		Question question = op.get();
		this.questionRepository.delete(question);
		assertThat(this.questionRepository.count()).isEqualTo(2);
	}

	/* 답변 데이터 생성 */
	@Test
	void insertAnswerTest() {
		Optional<Question> op = this.questionRepository.findById(2);
		assertTrue(op.isPresent());
		Question question = op.get();

		Answer answer1 = new Answer();
		answer1.setContent("네 자동으로 생성됩니다.");
		answer1.setQuestion(question);
		answer1.setCreateDate(LocalDateTime.now());

		Answer answer2 = new Answer();
		answer2.setContent("맞아요 자동으로 생성되더라고요");
		answer2.setQuestion(question);
		answer2.setCreateDate(LocalDateTime.now());

		this.answerRepository.save(answer1);
		this.answerRepository.save(answer2);
	}

	/* 답변 데이터 조회 */
	@Test
	void findByIdInAnswer() {
		Optional<Answer> oa = this.answerRepository.findById(1);
		assertTrue(oa.isPresent());
		Answer answer = oa.get();
		assertThat(answer.getContent()).isEqualTo("네 자동으로 생성됩니다.");
	}

	/* 답변 데이터 -> 질문 데이터 찾기 */
	@Test
	void findQuestionFromAnswer() {
		Optional<Answer> oa = this.answerRepository.findById(1);
		assertTrue(oa.isPresent());
		Answer answer = oa.get();
		assertThat(answer.getQuestion().getId()).isEqualTo(2);
	}

	/* 질문 데이터 -> 답변 데이터 찾기 */
	@Test
	@Transactional
	void findAnswerFromQuestion() {
		Optional<Question> oq = this.questionRepository.findById(2);
		assertTrue(oq.isPresent());
		Question question = oq.get();

		List<Answer> answerList = question.getAnswerList();
		assertThat(answerList.size()).isEqualTo(2);
		Answer answer = answerList.get(0);
		assertThat(answer.getContent()).isEqualTo("네 자동으로 생성됩니다.");
	}

}
