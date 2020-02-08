import application.DigitalWalletAPI
import com.fasterxml.jackson.databind.ObjectMapper
import io.javalin.Javalin
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import com.github.kittinunf.fuel.*
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.jackson.responseObject
import models.*
import org.junit.After

/**
 * @author Fabian Frangella
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class DigitalWalletAPITest {
    private lateinit var api: Javalin

    @BeforeAll
    fun setUp() {
        api = DigitalWalletAPI(8000).init()
        // Inject the base path to no have repeat the whole URL
        FuelManager.instance.basePath = "http://localhost:${api.port()}/"
    }

    @AfterAll
    fun tearDown() {
        api.stop()
    }

    @Test
    @Order(1)
    fun `1 POST login user`() {
        val mapper = ObjectMapper()
        val userLogin = LoginModel("esperanza@gmail.com","esperanza")
        val (_, response,_) = Fuel.post("login").jsonBody(mapper.writeValueAsString(userLogin)).response()
        assertEquals(201, response.statusCode)
    }

    @Test
    @Order(2)
    fun `2 POST register user and retrieve it by GET users`() {
        val mapper = ObjectMapper()
        val userPost = UserRegisterModel(
                idCard = "1234",
                firstName = "testName",
                lastName = "testLastName",
                email = "test@email.com",
                password = "testPassword"
        )
        val (_, responsePost, _) = Fuel.post("register").jsonBody(mapper.writeValueAsString(userPost)).response()
        assertEquals(201, responsePost.statusCode)
    }

    @Test
    @Order(3)
    fun `3 GET retrieve account balance by cvu`() {
        val (_, response, result) = Fuel.get("account/060065243").responseObject<BalanceModel>()
        assertEquals(200, response.statusCode)
        assertEquals(0.0,result.get().amount)
    }

    @Test
    @Order(4)
    fun `4 POST make a cashIn with a Debit Card and retrieve account balance by GET balance `() {
        val mapper = ObjectMapper()
        val cashIn = CashInModel(
                cvu = "060065243",
                amount = 9999.0,
                cardNumber = "1234 1234 1456 1234",
                fullName = "a a",
                endDate = "07/2019",
                securityCode = "123",
                cardType = "debit"
        )
        // cash in to cvu 060065243 for $9999
        val (_, responsePost, _) = Fuel.post("cashIn").jsonBody(mapper.writeValueAsString(cashIn)).response()
        assertEquals(201, responsePost.statusCode)
        // get balance from cvu 060065243 it should be $9999
        val (_, response, result) = Fuel.get("account/060065243").responseObject<BalanceModel>()
        assertEquals(200, response.statusCode)
        assertEquals(9999.0,result.get().amount)
    }

    @Test
    @Order(5)
    fun `5 POST make a transfer and retrieve it by GET balance `() {
        val mapper = ObjectMapper()
        val transferModel = TransferModel(
                fromCVU = "060065243",
                toCVU = "519264035",
                amount = 9999.0
        )
        // transfer $9999 from cvu 060065243 to cvu 519264035
        val (_, responseTransfer, _) = Fuel.post("transfer").jsonBody(mapper.writeValueAsString(transferModel)).response()
        assertEquals(200, responseTransfer.statusCode)

        // get balance from cvu 519264035 it should be $9999
        val (_, response, result) = Fuel.get("account/519264035").responseObject<BalanceModel>()
        assertEquals(200, response.statusCode)
        assertEquals(9999.0,result.get().amount)
    }

    @Test
    @Order(6)
    fun `6 GET get all transactions of cvu 060065243 `() {
        val (_, response, result) = Fuel.get("transactions/060065243").responseObject<List<TransactionalModel>>()
        assertEquals(200,response.statusCode)
        assertEquals(2,result.get().size)
    }

    @Test
    @Order(7)
    fun `7 DELETE user by cvu 060065243`(){
        val (_, response,_) = Fuel.delete("users/060065243").response()
        assertEquals(200, response.statusCode)
    }
}