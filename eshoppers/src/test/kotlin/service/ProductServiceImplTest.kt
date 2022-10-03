package service

import com.shihabmahamud.eshoppers.dto.ProductDTO
import com.shihabmahamud.eshoppers.repository.ProductRepository
import com.shihabmahamud.eshoppers.service.ProductService
import com.shihabmahamud.eshoppers.service.ProductServiceImpl
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import java.math.BigDecimal

class ProductServiceImplTest {
    private var productRepository: ProductRepository? = null
    private var productService: ProductService? = null
    @Before
    @Throws(Exception::class)
    fun setUp() {
        productRepository = Mockito.mock(ProductRepository::class.java)
        productService = ProductServiceImpl(productRepository)
    }

    @Test
    fun testFindAllProductSortedByName() {
        Mockito.`when`(productRepository!!.findAllProduct())
            .thenReturn(listOf(HEADPHONE, APPLE_I_PED))
        val sortedByName = productService!!.findAllProductSortedByName()
        Assert.assertEquals(APPLE_I_PED.name, sortedByName[0].name)
        Assert.assertEquals(HEADPHONE.name, sortedByName[1].name)
    }

    companion object {
        private val APPLE_I_PED = ProductDTO(
            "Apple iPad",
            "Apple iPad 10.2 32GB",
            BigDecimal.valueOf(369.99),
            4.9
        )
        val HEADPHONE = ProductDTO(
            "Headphones",
            "Jabra Elite Bluetooth Headphones",
            BigDecimal.valueOf(249.99),
            4.2
        )
    }
}