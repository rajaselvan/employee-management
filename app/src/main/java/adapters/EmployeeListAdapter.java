package adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;
import com.ideas2it.employeemanagement.R;
import java.util.ArrayList;
import models.Employee;
import android.widget.BaseAdapter;

import java.util.List;

public class EmployeeListAdapter extends RecyclerView.Adapter<EmployeeListAdapter.ViewHolder> {

    private Context mContext;
    private List<Employee> mDataSource = new ArrayList<Employee>();
    private List<Employee> filteredList;


    public EmployeeListAdapter(Context context, List<Employee> employeeList) {
        mContext = context;
        mDataSource = employeeList;
    }

    public List<Employee> fetchAll() {
        return mDataSource;
    }


    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults filterResults = new FilterResults();
                final ArrayList<Employee> results = new ArrayList<Employee>();
                if (filteredList == null)
                    filteredList = mDataSource;
                if (constraint != null) {
                    if (filteredList != null && filteredList.size() > 0) {
                        for (final Employee employee : filteredList) {
                            if (employee.getEmployeeName()
                                    .contains(constraint.toString()))
                                results.add(employee);
                        }
                    }
                    filterResults.values = results;
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                mDataSource = (ArrayList<Employee>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final Context context = parent.getContext();
        return new ViewHolder(context, LayoutInflater.from(context)
                .inflate(R.layout.custom_row_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TextView nameTextView = holder.nameTextView;
        TextView ageTextView = holder.ageTextView;
        TextView dobTextView = holder.dobTextView;
        TextView dojTextView = holder.dojTextView;
        Employee employee =  mDataSource.get(position);
        nameTextView.setText(employee.getEmployeeName());
        ageTextView.setText(String.format("Age: %1$s", employee.getEmployeeAge()));
        dobTextView.setText(String.format("DoB: %1$s", employee.getEmployeeDOB()));
        dojTextView.setText(String.format("DoJ: %1$s", employee.getEmployeeDOJ()));
    }

    @Override
    public int getItemCount() {
        return mDataSource.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nameTextView;
        public TextView ageTextView;
        public TextView dobTextView;
        public TextView dojTextView;

        public ViewHolder(final Context context, View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.display_emp_name);
            ageTextView = (TextView) itemView.findViewById(R.id.display_emp_age);
            dobTextView = (TextView) itemView.findViewById(R.id.display_emp_dob);
            dojTextView = (TextView) itemView.findViewById(R.id.display_emp_doj);
        }
    }
}
