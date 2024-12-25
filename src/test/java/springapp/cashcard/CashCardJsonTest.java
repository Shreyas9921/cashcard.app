package springapp.cashcard;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class CashCardJsonTest {

    @Autowired
    private JacksonTester<CashCard> json;

    @Autowired
    private JacksonTester<CashCard[]> jsonList;

    private CashCard[] cashCards;

    @BeforeEach
    void setUp() {
        cashCards = Arrays.array(
                new CashCard(99L, 123.45, "sarah1"),
                new CashCard(100L, 1.00, "sarah1"),
                new CashCard(101L, 150.00, "sarah1"));
    }

    @Test
    void cashCardSerializationTest() throws IOException {
        CashCard cashCard = cashCards[0];
        assertThat(json.write(cashCard)).isStrictlyEqualToJson("../../resource/single.json");
        assertThat(json.write(cashCard)).hasJsonPathNumberValue("@.id");
        assertThat(json.write(cashCard)).extractingJsonPathNumberValue("@.id")
                .isEqualTo(99);
        assertThat(json.write(cashCard)).hasJsonPathNumberValue("@.amount");
        assertThat(json.write(cashCard)).extractingJsonPathNumberValue("@.amount")
                .isEqualTo(123.45);
        assertThat(json.write(cashCard)).hasJsonPathStringValue("@.owner", cashCards);
        assertThat(json.write(cashCard)).extractingJsonPathStringValue("@.owner", cashCards);
    }

    @Test
    void cashCardDeserializationTest() throws IOException {
        String expected = """
                {
                    "id": 99,
                    "amount": 123.45, 
                    "owner": "sarah1"
                }
                """;
        assertThat(json.parse(expected))
                .isEqualTo(new CashCard(99L, 123.45, "sarah1"));
        assertThat(json.parseObject(expected).id()).isEqualTo(99L);
        assertThat(json.parseObject(expected).amount()).isEqualTo(123.45);
        assertThat(json.parseObject(expected).owner()).isEqualTo("sarah1");

    }

    @Test
    void cashCardListSerializationTest() throws IOException {
        assertThat(jsonList.write(cashCards)).isStrictlyEqualToJson("../../resource/list.json");
    }

    @Test
    void cashCardListDeserializationTest() throws IOException {
        String expected = """
                [
                     {"id": 99, "amount": 123.45 , "owner": "sarah1"},
                     {"id": 100, "amount": 1.00 , "owner": "sarah1"},
                     {"id": 101, "amount": 150.00, "owner": "sarah1"}
                                                  
                ]
                """;
        assertThat(jsonList.parse(expected)).isEqualTo(cashCards);
    }
}


/*
import static org.assertj.core.api.Assertions.assertThat;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

@JsonTest
class CashCardJsonTest {


	 /* @Test void myFirstTest() { assertThat(42).isEqualTo(42); }
	 
	
	@Autowired
	private JacksonTester<CashCard> json;
	
	@Test
	void cashCardSerializationTest() throws IOException {

		CashCard cashCard = new CashCard(99L, 123.45);
		
		assertThat(json.write(cashCard)).isStrictlyEqualToJson("../../resource/expected.json");
		
		assertThat(json.write(cashCard)).hasJsonPathNumberValue("@.id");
		
		assertThat(json.write(cashCard)).extractingJsonPathNumberValue("@.id").
					isEqualTo(99);
		
		assertThat(json.write(cashCard)).hasJsonPathNumberValue("@.amount");
		
		assertThat(json.write(cashCard)).extractingJsonPathNumberValue("@.amount").
					isEqualTo(123.45);
	}
	
	@Test
	void cashCardDesrializationTest() throws IOException {
		String expected = """
				{
					"id":99,
					"amount":123.45
				}
				
				""";
		assertThat(json.parse(expected))
					.isEqualTo(new CashCard(99L, 123.45));
		assertThat(json.parseObject(expected).id()).isEqualTo(99);
		
		assertThat(json.parseObject(expected).amount()).isEqualTo(123.45);
	}
	
}
*/