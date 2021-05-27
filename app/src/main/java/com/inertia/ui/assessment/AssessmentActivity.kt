package com.inertia.ui.assessment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.inertia.R
import com.inertia.data.datasource.remote.api.InertiaService
import com.inertia.data.entity.SpinnerKeyValue
import com.inertia.data.response.JenisBencanaResponse
import com.inertia.data.response.SkalaResponse
import com.inertia.data.response.SubSektorResponse
import com.inertia.databinding.ActivityAssessmentBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AssessmentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAssessmentBinding
    private var alternatifs : ArrayList<SkalaResponse> = ArrayList()
    private var jenisBencanas : ArrayList<JenisBencanaResponse> = ArrayList()
    private var subSektors : ArrayList<SubSektorResponse> = ArrayList()

    private var jenisBencanaItem: String? = null
    private var subSektorItem: String? = null
    private var alternatifValue1: String? = null
    private var alternatifValue2: String? = null
    private var alternatifValue3: String? = null
    private var alternatifValue4: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAssessmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initSpinner()
    }

    private fun kirimAssessment() {
        binding.apply {
            if (edtName.text?.isEmpty() == true) {
                edtName.error = "Kolom harus diisi"
                edtName.requestFocus()
                return
            } else if (edtAlamat.text?.isEmpty() == true) {
                edtAlamat.error = "Kolom harus diisi"
                edtAlamat.requestFocus()
                return
            } else {
                finish()
            }
        }
    }

    private fun initSpinner() {
        val spJenisBencana = binding.spinnerJenisBencana
        InertiaService().getPenilaian().getJenisBencanas().enqueue(object :
            Callback<List<JenisBencanaResponse>> {
            override fun onResponse(
                call: Call<List<JenisBencanaResponse>>,
                response: Response<List<JenisBencanaResponse>>
            ) {
                val data = response.body()
                if (data != null) {
                    jenisBencanas = response.body() as ArrayList<JenisBencanaResponse>
                    val data : ArrayList<SpinnerKeyValue> = ArrayList()
                    jenisBencanas.forEach {
                        val s = SpinnerKeyValue()
                        s.key = it.id
                        s.value = it.nmBencana
                        data.add(s)
                    }
                    val adapter: ArrayAdapter<SpinnerKeyValue> = ArrayAdapter<SpinnerKeyValue>(this@AssessmentActivity, R.layout.spinner_item, data)
                    spJenisBencana.adapter = adapter
                    spJenisBencana.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                        override fun onNothingSelected(parent: AdapterView<*>?) {
                        }
                        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                            jenisBencanaItem = adapter.getItem(position)?.key.toString()
                        }

                    }
                }
            }

            override fun onFailure(call: Call<List<JenisBencanaResponse>>, t: Throwable) {
                Log.e("Jenis Bencana", "Error: ${t.message}")
            }
        })

        val spSubSektor = binding.spinnerSubSektor
        InertiaService().getPenilaian().getSubSektors().enqueue(object :
            Callback<List<SubSektorResponse>> {
            override fun onResponse(
                call: Call<List<SubSektorResponse>>,
                response: Response<List<SubSektorResponse>>
            ) {
                val data = response.body()
                if (data != null) {
                    subSektors = response.body() as ArrayList<SubSektorResponse>
                    val data : ArrayList<SpinnerKeyValue> = ArrayList()
                    subSektors.forEach {
                        val s = SpinnerKeyValue()
                        s.key = it.id
                        s.value = it.nmSubSektor
                        data.add(s)
                    }
                    val adapter: ArrayAdapter<SpinnerKeyValue> = ArrayAdapter<SpinnerKeyValue>(this@AssessmentActivity, R.layout.spinner_item, data)
                    spSubSektor.adapter = adapter
                    spSubSektor.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                        override fun onNothingSelected(parent: AdapterView<*>?) {

                        }

                        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                            subSektorItem = adapter.getItem(position)?.key.toString()
                        }

                    }
                }
            }

            override fun onFailure(call: Call<List<SubSektorResponse>>, t: Throwable) {
                Log.e("Alternatif", "Error: ${t.message}")
            }
        })

        val spKeadaanBangunan = binding.spinnerKriteria1
        val spKeadaanStrukturBangunan = binding.spinnerKriteria2
        val spKeadaanFisikBangunan = binding.spinnerKriteria3
        val spFungsiBangunan = binding.spinnerKriteria4
        val spKeadaanLainnya = binding.spinnerKriteria5

        InertiaService().getPenilaian().getSkalas().enqueue(object :
            Callback<List<SkalaResponse>> {
            override fun onResponse(
                call: Call<List<SkalaResponse>>,
                response: Response<List<SkalaResponse>>
            ) {
                val data = response.body()
                if (data != null) {
                    alternatifs = response.body() as ArrayList<SkalaResponse>
                    val data : ArrayList<SpinnerKeyValue> = ArrayList()
                    alternatifs.forEach {
                        val s = SpinnerKeyValue()
                        s.key = it.id
                        s.value = it.nmSkala
                        data.add(s)
                    }

                    val adapter: ArrayAdapter<SpinnerKeyValue> = ArrayAdapter<SpinnerKeyValue>(this@AssessmentActivity, R.layout.spinner_item, data)
                    spKeadaanBangunan.adapter = adapter
                    spKeadaanStrukturBangunan.adapter = adapter
                    spKeadaanFisikBangunan.adapter = adapter
                    spFungsiBangunan.adapter = adapter
                    spKeadaanLainnya.adapter = adapter

                    spKeadaanBangunan.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                        override fun onNothingSelected(parent: AdapterView<*>?) {

                        }

                        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                            alternatifValue1 = adapter.getItem(position)?.key.toString()
                        }

                    }

                    spKeadaanStrukturBangunan.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                        override fun onNothingSelected(parent: AdapterView<*>?) {

                        }

                        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                            alternatifValue2 = adapter.getItem(position)?.key.toString()
                        }

                    }

                    spKeadaanFisikBangunan.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                        override fun onNothingSelected(parent: AdapterView<*>?) {

                        }

                        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                            alternatifValue3 = adapter.getItem(position)?.key.toString()
                        }

                    }

                    spKeadaanLainnya.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                        override fun onNothingSelected(parent: AdapterView<*>?) {

                        }

                        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                            alternatifValue4 = adapter.getItem(position)?.key.toString()
                        }
                    }
                }
            }
            override fun onFailure(call: Call<List<SkalaResponse>>, t: Throwable) {
                Log.e("Alternatif", "Error: ${t.message}")
            }
        })
    }
}