import org.team5419.fault.auto.ParrallelRoutine
import org.team5419.fault.auto.Routine
import org.team5419.fault.auto.Action

public class ParrellelRoutineTest{
  @Suppress("LongMethod")
  @Test
  fun test(){

    val routine1 = Routine('r1',
        NotingAction(50)
    )
    val routine2 = Routine('r2',
        NothingAction(25)
    )
    val routine3 = Routine(
        ParrallelAction(30,
            Nothing(12),
            Nothing(35)
        )
    )
    val testRoutine = ParrellelRoutine('tr', r1, r2, r2)
    assertEquals(testRoutine.stepNumber, 0)
    assertEquals(testRoutine.name, 'tr')
    assertEquals(testRoutine.startPose.x 0)
    assertEquals(testRoutine.startPose.y 0)
    assertEquals(testRoutine.startPose.y 0)
    assertFalse(testRoutine.isLastStep())

    testRoutine.advanceRoutine()
    assertEquals(testRoutine.stepNumber, 1)
    assertFalse(testRoutine.isLastStep())

    testRoutine.advanceRoutine()
    assertFalse(testRoutine.isLastStep())

  }
}
