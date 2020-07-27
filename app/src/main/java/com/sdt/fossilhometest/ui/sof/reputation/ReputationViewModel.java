package com.sdt.fossilhometest.ui.sof.reputation;

import com.sdt.fossilhometest.data.source.user.UserRepository;
import com.sdt.fossilhometest.ui.base.BaseViewModel;
import com.sdt.fossilhometest.utils.rx.SchedulerProvider;

import javax.inject.Inject;

public class ReputationViewModel extends BaseViewModel {

    private final UserRepository userRepository;

    @Inject
    public ReputationViewModel(SchedulerProvider schedulerProvider,
                               UserRepository userRepository) {
        super(schedulerProvider);
        this.userRepository = userRepository;
    }

}