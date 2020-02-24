SELECT
   *
FROM employee  e where e.birthday > TO_DATE('01-JAN-20','DD-MON-YY') and e.birthday < TO_DATE('13-FEB-20','DD-MON-YY');
