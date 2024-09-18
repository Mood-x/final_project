# namaa-final-project
Mohammed Abdullah Alomari


- مشروع نماء:
(نماء) في اللغة العربية تعني الزيادة، وتتضمن الزيادة المستمرة كنمو الشجرة. هذا هو هدف المشروع، النمو. يهتم مشروع نماء بالاطفال في مرحلة نموهم من خلال توفير لهم التعلم والخبرة والتحديات. يحل هذا النظام مشكلة نقص المصادر لإيجاد برامج تستهدف المهارة المطلوبة للطفل. 
يجمع نظام نماء جميع مراكز الأنشطة من مختلف الأماكن و مختلف المهارات مثل: الرياضة والفن والأولمبياد والتعليم والمزيد. أيضا يمكن للوالديّن من خلال نظام نماء انشاء حسابات شخصية و إضافة أطفال تابعين لهم وتسجيلهم في برامج نشاطية و مسابقات ومتابعة أدائهم مع حصولهم  على شهادات عند اكمالهم البرنامج النشاطي.


-namaa project:

(Namaa) in Arabic means increase, and includes continuous increase like the growth of a tree. This is the goal of the project. Growth is concerned with young students until they grow up through learning, experience and challenges. This system solves the problem of the lack of resources for finding programs to target the child’s required skill. It brings together all activity centers from everywhere and all skills: sports, art, Olympics, education and more. It allows the parents to register their children, track their performance and receive certificates.


=============================================================================


- namaa UseCase diagram:

![namaa useCase](https://github.com/user-attachments/assets/954193f9-cc6e-47b9-a3f5-bb120ce6f689)


=============================================================================


-namma Class diagram:
[Namaa class diagram.pdf](https://github.com/user-attachments/files/17036485/Namaa.class.diagram.pdf)


=============================================================================


PostMan documnts Url:
https://documenter.getpostman.com/view/30184159/2sAXqp94W8

Figma Url:
https://www.figma.com/design/NepUkGbwTIp0ClKUYY0evQ/Untitled?node-id=43-795&t=sr8VAQWjZwXIt156-1


Presentation Url:
https://www.canva.com/design/DAGRAW7UYs0/GYRJ9MorfaMRkSTe14uk-w/edit?utm_content=DAGRAW7UYs0&utm_campaign=designshare&utm_medium=link2&utm_source=sharebutton
=============================================================================

My Endpoints and Models:

- Models:

  User | Notification | Competition | ChildProgres
===================== Endpoints =====================

AuthController:
  -- getAllUsers()
  -- requestAccountDeletion()
  -- cancelAccountDeletion()

Notificaiton:
  -- getAllMyNotifications()
  -- getNotificationById()
  -- addNotification()
  -- updateNotification()
  -- deleteNotification()
  -- deleteAllMyNotifications()
  -- deleteAllMyNotificationsIsRead()
  -- readNotification()
  -- markAllAsRead()
  -- getAllNotificationsIsNotReading()

Competition:
  -- getAllCompetitions()
  -- getCompetitionsById()
  -- addCompetition()
  -- updateCompetition()
  -- deleteCompetition()
  -- searchCompetitionByDate()
  -- searchCompetitionByName()
  -- searchCompetitionsByType()
  -- searchCompetitionsByAgeRange()
  -- getAllCompetitionsForChild()
  -- participationRequest()
  -- approvedParticipationRequest()
  -- rejectParticipationRequest()
  -- cancelParticipation()

ChildProgress:
  -- getAllChildProgress()
  -- getChildProgressByProgramId()
  -- getAllChildProgressByChildId()
  -- createChildProgresss()
  -- modifyChildProgress()
  -- deleteChildProgress()

===================== Methods =====================

AuthService
  -- deletionAccountAfter10Days()

NotificationService
  -- createNotification()

ChildProgress
  -- createChildProgress()
  -- calculateRating()
  -- calculateProgressLevel()
  -- calculateAttendancePercentage()