package br.com.caelum.diabetes.fragment.calculadora;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import br.com.caelum.diabetes.R;
import br.com.caelum.diabetes.activity.MainActivity;
import br.com.caelum.diabetes.dao.AlimentoFisicoDao;
import br.com.caelum.diabetes.dao.DbHelper;
import br.com.caelum.diabetes.extras.UnidadeMedidaAlimento;
import br.com.caelum.diabetes.extras.ValidaCampos;
import br.com.caelum.diabetes.model.AlimentoFisico;
import br.com.caelum.diabetes.util.ValidatorUtils;

public class NovoAlimentoDiferenteFragment extends Fragment {
	private EditText nomeAlimento;
	private EditText carboidrato;
	private AutoCompleteTextView unidadeMedida;
	private Button salvarAlimento;

	@Override
	public void onResume() {
		super.onResume();
        ((MainActivity) getActivity()).setTitleHeader("Adicionar Novo Alimento");
        ((MainActivity) getActivity()).setBackArrowIcon();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.novo_alimento_diferente, null);

		nomeAlimento = (EditText) view.findViewById(R.id.nome_alimento);
		unidadeMedida = (AutoCompleteTextView) view
				.findViewById(R.id.unidade_novo_alimento);
		carboidrato = (EditText) view
				.findViewById(R.id.carboidrato_novo_alimento);
		salvarAlimento = (Button) view.findViewById(R.id.salvar_alimento);
		ValidaCampos.validateEditText(nomeAlimento, salvarAlimento);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_dropdown_item_1line,
				UnidadeMedidaAlimento.getAll());
		unidadeMedida.setAdapter(adapter);
		salvarAlimento.setEnabled(ValidatorUtils
				.checkEmptyEditText(nomeAlimento));
		salvarAlimento.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				AlimentoFisico alimentoFisico = new AlimentoFisico(nomeAlimento
						.getText().toString(), Double.parseDouble(carboidrato
						.getText().toString()), unidadeMedida.getText()
						.toString());
				DbHelper helper = new DbHelper(getActivity());
				AlimentoFisicoDao dao = new AlimentoFisicoDao(helper);
				dao.salve(alimentoFisico);
				helper.close();

				getFragmentManager().popBackStack();
			}
		});

		return view;
	}
}
