package com.pheduarte.finecontrol.ui.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.pheduarte.finecontrol.R;
import com.pheduarte.finecontrol.data.Transactions;
import com.pheduarte.finecontrol.databinding.FragmentTransactionsBinding;
import com.pheduarte.finecontrol.data.TransactionAdapter;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;


public class  TransactionsFragment extends Fragment {

    private FragmentTransactionsBinding binding;

    public static TransactionsFragment newInstance() { return new TransactionsFragment(); }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentTransactionsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize RecyclerView and its adapter
        RecyclerView recyclerView = view.findViewById(R.id.recyclerTransactions);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        TransactionAdapter adapter = new TransactionAdapter();
        recyclerView.setAdapter(adapter);

        TransactionsViewModel viewModel = new ViewModelProvider(this).get(TransactionsViewModel.class);

        // Captures logged in user's email to link with transactions
        SharedPreferences prefs = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        String email = prefs.getString("logged_in_email", null);

         //Retrieves and displays transactions for the logged in user
        if (email != null) {
            viewModel.getTransactionsByUser(email).observe(getViewLifecycleOwner(), transactions -> {
                if (transactions == null || transactions.isEmpty()) {
                    binding.recyclerTransactions.setVisibility(View.GONE);
                    binding.tvEmptyList.setVisibility(View.VISIBLE);
                } else {
                    binding.recyclerTransactions.setVisibility(View.VISIBLE);
                    binding.tvEmptyList.setVisibility(View.GONE);
                    adapter.setTransactions(transactions);
                }
            });
        }

        // Search functionality
        binding.textSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String query = s.toString().trim();

                if (email != null) {
                    viewModel.searchTransactions(email, query)
                            .observe(getViewLifecycleOwner(), results -> {
                                if (results == null || results.isEmpty()) {
                                    binding.recyclerTransactions.setVisibility(View.GONE);
                                    binding.tvEmptyList.setVisibility(View.VISIBLE);
                                } else {
                                    binding.recyclerTransactions.setVisibility(View.VISIBLE);
                                    binding.tvEmptyList.setVisibility(View.GONE);
                                    adapter.setTransactions(results);
                                }
                            });
                }
            }
        });

        //Filter functionality
        binding.btnFilter.setOnClickListener(v -> {
            androidx.appcompat.widget.PopupMenu popup =
                    new androidx.appcompat.widget.PopupMenu(requireContext(), binding.btnFilter);

            popup.getMenuInflater().inflate(R.menu.menu_filter, popup.getMenu());

            popup.setOnMenuItemClickListener(item -> {
                int id = item.getItemId();

                if (id == R.id.filter_all) {
                    viewModel.getTransactionsByUser(email)
                            .observe(getViewLifecycleOwner(), adapter::setTransactions);
                    return true;

                } else if (id == R.id.filter_income) {
                    viewModel.filterByType(email, "income")
                            .observe(getViewLifecycleOwner(), adapter::setTransactions);
                    return true;

                } else if (id == R.id.filter_expense) {
                    viewModel.filterByType(email, "expense")
                            .observe(getViewLifecycleOwner(), adapter::setTransactions);
                    return true;

                } else if (id == R.id.filter_food) {
                    viewModel.filterByType(email, "food")
                            .observe(getViewLifecycleOwner(), adapter::setTransactions);
                    return true;

                } else if (id == R.id.filter_transport) {
                    viewModel.filterByType(email, "transport")
                            .observe(getViewLifecycleOwner(), adapter::setTransactions);
                    return true;

                } else if (id == R.id.filter_shopping) {
                    viewModel.filterByType(email, "shopping")
                            .observe(getViewLifecycleOwner(), adapter::setTransactions);
                    return true;

                } else if (id == R.id.filter_house) {
                    viewModel.filterByType(email, "house")
                            .observe(getViewLifecycleOwner(), adapter::setTransactions);
                    return true;

                } else if (id == R.id.filter_Subscription) {
                    viewModel.filterByType(email, "subscription")
                            .observe(getViewLifecycleOwner(), adapter::setTransactions);
                    return true;

                } else if (id == R.id.filter_bills) {
                    viewModel.filterByType(email, "bills")
                            .observe(getViewLifecycleOwner(), adapter::setTransactions);
                    return true;

                } else if (id == R.id.filter_health) {
                    viewModel.filterByType(email, "health")
                            .observe(getViewLifecycleOwner(), adapter::setTransactions);
                    return true;

                } else if (id == R.id.filter_salary) {
                    viewModel.filterByType(email, "salary")
                            .observe(getViewLifecycleOwner(), adapter::setTransactions);
                    return true;

                } else if (id == R.id.filter_transfer) {
                    viewModel.filterByType(email, "transfer")
                            .observe(getViewLifecycleOwner(), adapter::setTransactions);
                    return true;

                } else if (id == R.id.filter_others) {
                    viewModel.filterByType(email, "others")
                            .observe(getViewLifecycleOwner(), adapter::setTransactions);
                    return true;
                }

                return false;
            });


            popup.show();
        });





        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                // Get the transaction being swiped
                Transactions deletedTransaction = adapter.getCurrentList().get(position);

                // Delete from database via ViewModel
                viewModel.deleteTransactions(deletedTransaction.getTransactionId());

                // Remove from adapter’s list (you might use submitList if using ListAdapter)
                adapter.notifyItemRemoved(position);

                // Show Snackbar with Undo option
                Snackbar.make(binding.getRoot(), "Transaction deleted", Snackbar.LENGTH_LONG)
                        .setAction("UNDO", v -> {
                            viewModel.insert(deletedTransaction);
                        })
                        .show();
            }

            // Add swipe effect when deleting transactions
            @Override
            public void onChildDraw(@NonNull Canvas c,
                                    @NonNull RecyclerView recyclerView,
                                    @NonNull RecyclerView.ViewHolder viewHolder,
                                    float dX, float dY,
                                    int actionState,
                                    boolean isCurrentlyActive) {

                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addSwipeLeftBackgroundColor(ContextCompat.getColor(requireContext(), R.color.redExpense))
                        .addSwipeLeftActionIcon(R.drawable.ic_delete) // your trash icon
                        .addSwipeRightBackgroundColor(ContextCompat.getColor(requireContext(), R.color.redExpense))
                        .addSwipeRightActionIcon(R.drawable.ic_delete)
                        .create()
                        .decorate();

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

        };

        // Attach to RecyclerView
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(binding.recyclerTransactions);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
