package org.progress.crm.exceptions;

public class PermissionsDeniedException extends CustomException {
    
    @Override
    public String getMessage() {
        return "У вас недостаточно прав для совершения данного действия";
    }
}
