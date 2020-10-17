    package es.iessaladillo.pedrojoya.exchange

    import android.os.Bundle
    import android.text.Editable
    import android.text.TextWatcher
    import android.widget.Toast
    import androidx.appcompat.app.AppCompatActivity
    import es.iessaladillo.pedrojoya.exchange.databinding.MainActivityBinding
    import es.iessaladillo.pedrojoya.exchange.utils.hideSoftKeyboard


    class MainActivity : AppCompatActivity() {

        private lateinit var binding: MainActivityBinding
        private lateinit var firstCurr: Currency
        private lateinit var secondCurr: Currency

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            //Creo una clase ViewBinding que contiene todas las vistas y sus caracteristicas definidas en el archivo main_activity.xml
            //y las "infla" para que se muestren en la pantalla
            binding = MainActivityBinding.inflate(layoutInflater)
            //Se muestra el contenido de las vistas por la clase ViewBinding
            setContentView(binding.root)
            //Metodo que configura las vistas de la actividad cuando se crea
            setupViews()
        }

        private fun setupViews() {
            //Se resetea el numero introducido y se selecciona
            resetInputAmount()
            //Se crea un objeto de tipo interfaz funcional OnCheckedChangeListener que contiene un metodo
            //para mostrar el icono de la moneda seleccionada y deshabilitar la misma moneda en la seleccion contraria
            binding.firstCurrContainer.setOnCheckedChangeListener { _, option -> setFirstCurrencyOption(option) }
            binding.secondCurrContainer.setOnCheckedChangeListener { _, option -> setSecondCurrencyOption(option) }
            //Deshabilito la moneda que marco de forma predeterminada en el RadioGroup contrario
            binding.firstCurrPound.isEnabled = false
            binding.secondCurrDollar.isEnabled = false
            //Se crea un objeto de tipo interfaz TextWatcher que contiene un metodo
            //para realizar varias acciones al modificar el texto de la vista
            binding.mainAmountInput.addTextChangedListener(object: TextWatcher {
                override fun beforeTextChanged(s: CharSequence?,
                                               start: Int,
                                               count: Int,
                                               after: Int) {
                    //No se usa
                }

                override fun onTextChanged(s: CharSequence?,
                                           start: Int,
                                           before: Int,
                                           count: Int) {
                    //No se usa
                }

                override fun afterTextChanged(s: Editable?) {
                    //Cuando el numero se cambia, se comprueba que es valido
                    checkAmount()
                }
            })
            //Se crea un objeto de tipo interfaz funcional OnClickListener que contiene un metodo
            //para que se muestre el numero seleccionado cuando se pulsa sobre la vista
            binding.mainAmountInput.setOnClickListener { selectAmount() }
            //Se crea un objeto de tipo interfaz funcional OnClickListener que contiene un metodo
            //para que se muestre el resultado cuando se pulsa sobre el boton
            binding.mainButton.setOnClickListener { showMessage() }
            //Se asigna a la clase las monedas de las opciones predefinidas
            firstCurr = Currency.DOLLAR
            secondCurr = Currency.POUND
            //Se crea un objeto de tipo interfaz funcional OnEditorActionListener que contiene un metodo
            //para que se muestre el resultado cuando se pulsa el boton IME
            binding.mainAmountInput.setOnEditorActionListener { _, _, _ ->
                showMessage()
                true
            }
        }

        private fun resetInputAmount() {
            //Metodo que resetea el numero introducido
            //Cuando se resetea el numero, la vista recibe el foco, el numero se cambia a  y se selecciona
            binding.mainAmountInput.requestFocus()
            binding.mainAmountInput.setText("0")
            binding.mainAmountInput.selectAll()
        }

        private fun selectAmount() {
            //Se selecciona el numero que contiene
            binding.mainAmountInput.selectAll()
        }

        private fun setFirstCurrencyOption(option: Int) {
            //Metodo que recive el id de la opcion del primer RadioGroup y dependiendo de la opcion seleccionada
            //se deshabilita la misma moneda en el segundo RadioGroup
            if (option == binding.firstCurrDollar.id) {
                binding.secondCurrDollar.isEnabled = false
                binding.secondCurrEuro.isEnabled = true
                binding.secondCurrPound.isEnabled = true
                //La variable de clase se asigna a la moneda seleccionada
                firstCurr = Currency.DOLLAR
            }

            if (option == binding.firstCurrEuro.id) {
                binding.secondCurrDollar.isEnabled = true
                binding.secondCurrEuro.isEnabled = false
                binding.secondCurrPound.isEnabled = true
                firstCurr = Currency.EURO
            }

            if (option == binding.firstCurrPound.id) {
                binding.secondCurrDollar.isEnabled = true
                binding.secondCurrEuro.isEnabled = true
                binding.secondCurrPound.isEnabled = false
                firstCurr = Currency.POUND
            }
            //Se muestra el icono de la moneda seleccionada en el primer RadioGroup
            setIconCurrency(option)
        }

        private fun setSecondCurrencyOption(option: Int) {
            //Metodo que recive el id de la opcion del segundo RadioGroup y dependiendo de la opcion seleccionada
            //se deshabilita la misma moneda en el primer RadioGroup
            if (option == binding.secondCurrDollar.id) {
                binding.firstCurrDollar.isEnabled = false
                binding.firstCurrEuro.isEnabled = true
                binding.firstCurrPound.isEnabled = true
                secondCurr = Currency.DOLLAR
            }

            if (option == binding.secondCurrEuro.id) {
                binding.firstCurrDollar.isEnabled = true
                binding.firstCurrEuro.isEnabled = false
                binding.firstCurrPound.isEnabled = true
                secondCurr = Currency.EURO
            }

            if (option == binding.secondCurrPound.id) {
                binding.firstCurrDollar.isEnabled = true
                binding.firstCurrEuro.isEnabled = true
                binding.firstCurrPound.isEnabled = false
                secondCurr = Currency.POUND
            }
            //Se muestra el icono de la moneda seleccionada en el segundo RadioGroup
            setIconCurrency(option)
        }

        private fun checkAmount() {
            //Metodo que comprueba el numero introducido a convertir:
            //Si el valor recibido empieca por el punto decimal, o se quiere introducir más de un punto, entonces el valor
            //se vuelve a cambiar a 0, se vuelve a seleccionar y recibe de nuevo el foco
            if (binding.mainAmountInput.text.toString() == "" || binding.mainAmountInput.text.matches(Regex(pattern = "\\."))
                            || binding.mainAmountInput.text.matches(Regex(pattern = "[0-9]+\\.[0-9]*\\.+"))) {
                resetInputAmount()
            }
        }

        private fun setIconCurrency(option: Int) {
            //Metodo que recibe el id de la opcion seleccionada y muestra su imagen
            when (option) {
                binding.firstCurrDollar.id -> binding.iconFirstCurr.setImageResource(R.drawable.ic_dollar)
                binding.firstCurrEuro.id -> binding.iconFirstCurr.setImageResource(R.drawable.ic_euro)
                binding.firstCurrPound.id -> binding.iconFirstCurr.setImageResource(R.drawable.ic_pound)
                binding.secondCurrDollar.id -> binding.iconSecondCurr.setImageResource(R.drawable.ic_dollar)
                binding.secondCurrEuro.id -> binding.iconSecondCurr.setImageResource(R.drawable.ic_euro)
                binding.secondCurrPound.id -> binding.iconSecondCurr.setImageResource(R.drawable.ic_pound)
            }
        }

        private fun showMessage() {
            val text: String
            val result : Double
            val message : Toast
            if (secondCurr.symbol == Currency.DOLLAR.symbol) {
                //Si se va a convertir Euro o Libra A DOLAR, entonces solo uso el metodo toDollar() de la clase Currency
                result = firstCurr.toDollar(binding.mainAmountInput.text.toString().toDouble())
                text = String.format("%s %s = %.2f %s", binding.mainAmountInput.text, firstCurr.symbol, result, secondCurr.symbol)
            } else {
                //En cualquier otro caso, convierto la primera moneda a Dolar, y luego la convierto de Dolar a la moneda destino
                //No importa si la moneda de origen es el Dolar porque la cantidad es la misma
                result = secondCurr.fromDollar(firstCurr.toDollar(binding.mainAmountInput.text.toString().toDouble()))
                text = String.format("%s %s = %.2f %s", binding.mainAmountInput.text, firstCurr.symbol, result, secondCurr.symbol)
            }
            //Escondo el teclado antes de mostrar el resultado
            hideSoftKeyboard(binding.mainButton)
            message = Toast.makeText(this, text, Toast.LENGTH_SHORT)
            message.show()
        }
    }
