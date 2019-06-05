/**
 * Generic transaction fragment
 *
 * @author Kevin DO VALE, Aleksandar MILENKOVIC
 * @version 1.0
 * @see ch.heig.cashflow.adapters.transactions.TransactionAddOrEditAdapter
 */

package ch.heig.cashflow.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import ch.heig.cashflow.R;
import ch.heig.cashflow.adapters.cards.TransactionCardsAdapter;
import ch.heig.cashflow.models.Transaction;
import ch.heig.cashflow.network.services.TransactionsService;
import ch.heig.cashflow.utils.ApplicationResources;
import ch.heig.cashflow.utils.Currency;
import ch.heig.cashflow.utils.SelectedDate;
import ch.heig.cashflow.utils.Type;

public class TransactionFragment extends Fragment implements TransactionsService.Callback, Observer {
    private View view;
    private TextView expenseView;
    private ListView expensesListView;
    private Type type = Type.EXPENSE;
    private long totalExpenses;

    /**
     * The required TransactionFragment empty public constructor
     */
    public TransactionFragment() {
        // Required empty public constructor
    }

    /**
     * Create a new TransactionFragment instance
     *
     * @return TransactionFragment A new TransactionFragment instance
     */
    public static TransactionFragment newInstance() {
        return new TransactionFragment();
    }

    /**
     * Save the parent instance state
     *
     * @param savedInstanceState The saved instance state
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * On view resume, refresh datas
     */
    @Override
    public void onResume() {
        super.onResume();
        SelectedDate.getInstance().addObserver(this);
        reload();
    }

    /**
     * Call the transaction service and ask him fresh datas by month for a {@code Type}
     */
    private void reload() {
        new TransactionsService(this).getTypeByMonth(type);
    }

    /**
     * Called to have the fragment instantiate its user interface view
     *
     * @param inflater           The LayoutInflater object that can be used to inflate any views in the fragment
     * @param container          If non-null, this is the parent view that the fragment's UI should be attached to.
     *                           The fragment should not add the view itself, but this can be used to generate
     *                           the LayoutParams of the view. This value may be null.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here
     * @return Return the View for the fragment's UI, or null.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_transaction, container, false);

        expenseView = view.findViewById(R.id.totalExpenses);
        expensesListView = view.findViewById(R.id.expenseCardView);

        return view;
    }

    /**
     * Display an error message if the API call failed
     *
     * @param error The error
     */
    @Override
    public void connectionFailed(String error) {
        if(getContext() == null) return;
        Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
    }

    /**
     * Executed when the API call is done
     *
     * <p>
     * Set and display the expenses list view with new fresh datas
     *
     * @param transactions The transactions list
     */
    @Override
    public void getFinished(List<Transaction> transactions) {

        ApplicationResources appRes = new ApplicationResources(getContext());

        totalExpenses = 0;
        for (Transaction t : transactions)
            totalExpenses += t.getAmount();

        expenseView.setText(Currency.format(totalExpenses));
        expenseView.setTextColor(type.equals(Type.EXPENSE) ? appRes.getColor(R.color.red) : appRes.getColor(R.color.green));

        if(getContext() == null) return;

        if (transactions.isEmpty()) {
            view.findViewById(R.id.expense_empty_layout).setBackground(getResources().getDrawable(R.drawable.emptyscreen));
        } else {
            view.findViewById(R.id.expense_empty_layout).setBackgroundColor(appRes.getColor(R.color.white));
        }

        expensesListView.setAdapter(new TransactionCardsAdapter(getActivity(), transactions, type));
    }

    /**
     * Set the transaction type
     *
     * @param type The {@code Type} to set
     */
    public void setType(Type type) {
        this.type = type;
    }

    /**
     * On observable update
     *
     * @param observable The observable
     * @param obj        The object
     */
    @Override
    public void update(Observable observable, Object obj) {
        reload();
    }
}
