package com.ash.teacheron;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DemoActivity extends AppCompatActivity {

    TextView txthd;
    String aboutContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_demo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        TextView aboutTextView = findViewById(R.id.tvcg);
        txthd=findViewById(R.id.txthd);
        int type=getIntent().getIntExtra("type",1);
        if (type==2)
        {
            txthd.setText("About Us");
              aboutContent = "<h2>About Us</h2>" +
                    "<p>Welcome to <b>Find Your Teacher</b>, your trusted platform for finding and connecting with qualified teachers, both online and offline. We believe education is the key to unlocking human potential, and our goal is to make quality learning accessible to everyone.</p>" +
                    "<p>Whether you're a student looking for guidance or a parent searching for the best tutor for your child, we bridge the gap between learners and educators, ensuring a seamless and effective learning experience.</p>" +

                    "<h2>Our Mission</h2>" +
                    "<p>At <b>Find Your Teacher</b>, our mission is to empower students by providing them access to the best teachers across various subjects and expertise levels.</p>" +
                    "<p>We strive to create an environment where learning is flexible, personalized, and efficient, helping individuals achieve their academic and personal growth goals.</p>" +

                    "<h2>Operating Principles</h2>" +
                    "<ul>" +
                    "<li><b>Quality First –</b> We ensure that every teacher on our platform meets high educational and professional standards.</li>" +
                    "<li><b>Accessibility –</b> Learning should be within reach for everyone, regardless of location or background.</li>" +
                    "<li><b>Security & Trust –</b> We prioritize the safety of students and teachers, ensuring secure communication and transactions.</li>" +
                    "<li><b>Flexibility –</b> Offering both online and offline options allows students to choose a learning method that best suits their needs.</li>" +
                    "<li><b>Continuous Improvement –</b> We are committed to enhancing our platform based on user feedback and technological advancements.</li>" +
                    "</ul>" +

                    "<h2>Our Commitment to You</h2>" +
                    "<p>At <b>Find Your Teacher</b>, we are dedicated to providing a seamless, reliable, and high-quality experience for both students and teachers. Our commitment includes:</p>" +
                    "<ul>" +
                    "<li>Carefully vetting teachers to ensure credibility and expertise.</li>" +
                    "<li>Offering a user-friendly interface for effortless browsing and booking.</li>" +
                    "<li>Providing support and resources to enhance the teaching and learning experience.</li>" +
                    "<li>Ensuring fair and transparent pricing.</li>" +
                    "<li>Maintaining a secure and respectful community for all users.</li>" +
                    "</ul>" +

                    "<h2>How Does the Website Work?</h2>" +
                    "<ol>" +
                    "<li><b>Search for a Teacher –</b> Browse our extensive list of qualified educators based on subject, location, and preferred learning method (online or offline).</li>" +
                    "<li><b>View Profiles & Reviews –</b> Explore teacher profiles, including qualifications, experience, and reviews from other students.</li>" +
                    "<li><b>Book a Lesson –</b> Select your preferred teacher and schedule a lesson at your convenience.</li>" +
                    "<li><b>Attend Your Class –</b> Join your session either online via our integrated platform or in person at an agreed location.</li>" +
                    "<li><b>Rate & Review –</b> Share your feedback to help improve the community experience for other learners.</li>" +
                    "</ol>" +

                    "<h2>Our Story</h2>" +
                    "<p><b>Find Your Teacher</b> was founded with a vision to simplify the process of finding the right teacher. Our journey began when we noticed the struggle students and parents faced in finding reliable educators.</p>" +
                    "<p>Traditional tutoring methods were either too expensive or inaccessible, and online platforms lacked a personal touch.</p>" +
                    "<p>Determined to bridge this gap, we created a platform that offers personalized learning experiences with certified teachers.</p>" +
                    "<p>By combining technology with education, we have built a network that connects students with passionate educators, making learning more engaging and effective.</p>" +
                    "<p>Today, we continue to expand our community, ensuring that knowledge reaches every corner of the world.</p>" +
                    "<p><b>Join us at Find Your Teacher and take the next step in your learning journey!</b></p>";

        }

        if (type==1)
        {
            txthd.setText("Contact Us");
            aboutContent ="<h3>Contact Us</h3>" +
            "<p>For any queries or concerns regarding this Privacy Policy, please contact us at:</p>" +
                    "<p><b>[Find Your Teacher]</b></p>" +
                    "<p>Email: <a href='mailto:[help@findyourteacher.com]'>[help@findyourteacher.com]</a></p>" +
                    "<p>Phone: <a href='tel:[0987654321]'>[0987654321]</a></p>";
        }


        if (type==3)
        {
            txthd.setText("Terms And Conditions");
            aboutContent =
                    "<h2>Terms And Conditions</h2>" +

                            "<h3>General Terms and Conditions</h3>" +
                            "<p><b>Acceptance of Terms:</b> By using this platform to search for teachers online or offline, you agree to comply with these Terms and Conditions. If you do not agree, you must stop use immediately.</p>" +
                            "<p><b>Platform Role:</b> The platform acts as an intermediary to connect students and teachers. It does not guarantee the quality, availability, or performance of any teacher.</p>" +
                            "<p><b>User Responsibility:</b> Both students and teachers are responsible for ensuring the accuracy of their provided information, including qualifications, availability, and expectations.</p>" +
                            "<p><b>Modifications:</b> The platform reserves the right to modify these Terms and Conditions at any time. Continued use of the platform after modifications constitutes acceptance of the revised terms.</p>" +
                            "<p><b>Privacy Policy:</b> User data is collected and stored in compliance with relevant data protection laws. By using this service, you consent to the processing of your data as outlined in the Privacy Policy.</p>" +
                            "<p><b>Prohibited Activities:</b> Users must not engage in fraudulent activities, harassment, discrimination, or any illegal conduct. Any violation may lead to termination of access.</p>" +
                            "<p><b>Dispute Resolution:</b> The platform is not responsible for disputes between students and teachers. Users are encouraged to resolve disputes amicably, and if needed, seek third-party mediation.</p>" +
                            "<p><b>Liability Limitation:</b> The platform is not liable for any direct or indirect damages resulting from the use of the service, including but not limited to financial loss, academic setbacks, or misrepresentation by users.</p>" +

                            "<h3>Terms and Conditions for Students</h3>" +
                            "<p><b>Account Registration:</b> Students must provide accurate personal and academic information when registering an account. False information may result in account suspension.</p>" +
                            "<p><b>Payment Terms:</b> If applicable, students must adhere to payment terms agreed upon with the teacher. The platform is not responsible for payment disputes unless explicitly managing transactions.</p>" +
                            "<p><b>Booking and Cancellations:</b> Students must adhere to the booking policies set by the teacher, including scheduling and cancellation terms. Last-minute cancellations may incur penalties.</p>" +
                            "<p><b>Code of Conduct:</b> Students must maintain respectful communication with teachers. Any form of abuse, harassment, or discrimination will result in account suspension.</p>" +
                            "<p><b>Feedback and Reviews:</b> Students may leave reviews for teachers. However, false or defamatory reviews may be removed and could lead to account suspension.</p>" +
                            "<p><b>Confidentiality:</b> Students must respect teachers’ proprietary materials and must not distribute or misuse any learning materials provided by teachers without permission.</p>" +

                            "<h3>Terms and Conditions for Teachers</h3>" +
                            "<p><b>Account Registration and Verification:</b> Teachers must provide accurate qualifications, experience details, and valid identification for verification. Any misrepresentation may result in immediate suspension.</p>" +
                            "<p><b>Lesson Scheduling:</b> Teachers must communicate their availability and adhere to agreed schedules. Repeated cancellations or no-shows may lead to account suspension.</p>" +
                            "<p><b>Teaching Standards:</b> Teachers must provide quality instruction in their respective subjects. They are responsible for preparing lessons, providing feedback, and maintaining professional conduct.</p>" +
                            "<p><b>Payment Agreements:</b> Teachers must set their fees transparently. If the platform manages payments, teachers must comply with the payment policies outlined in the service agreement.</p>" +
                            "<p><b>Student Interaction:</b> Teachers must maintain professional relationships with students and avoid any form of inappropriate behavior, discrimination, or harassment.</p>" +
                            "<p><b>Intellectual Property:</b> Teachers retain rights to their original teaching materials. However, by sharing materials through the platform, they grant students a limited license for personal use.</p>" +
                            "<p><b>Legal Compliance:</b> Teachers must comply with local laws and educational standards. Any unethical or illegal conduct will result in immediate termination and potential legal action.</p>" +
                            "<p><b>Dispute Handling:</b> Teachers must attempt to resolve disputes with students professionally. If unresolved, they may seek assistance from platform support.</p>" +

                            "<h3>Termination of Use</h3>" +
                            "<p><b>Breach of Terms:</b> Any user found violating these Terms and Conditions may have their access revoked without prior notice.</p>" +
                            "<p><b>Voluntary Termination:</b> Users may deactivate their accounts at any time, subject to any outstanding obligations, such as pending payments or lesson commitments.</p>" +
                            "<p><b>Platform Rights:</b> The platform reserves the right to suspend or terminate services for any user at its sole discretion, particularly in cases of policy violations.</p>" +

                            "<h3>Governing Law and Jurisdiction</h3>" +
                            "<p><b>Legal Compliance:</b> These Terms and Conditions are governed by the laws of the jurisdiction where the platform operates.</p>" +
                            "<p><b>Dispute Resolution:</b> Any legal disputes arising from these terms shall be resolved through arbitration or the appropriate legal forum in the governing jurisdiction.</p>" +

                            "<p><b>By using this service, you acknowledge that you have read, understood, and agreed to these Terms and Conditions.</b></p>";


        }


        if (type==4)
        {
            txthd.setText("Privacy Policy");
            aboutContent =
                    "<h2>Privacy Policy</h2>" +

                            "<p>Welcome to <b>Find Your Teacher</b>. Your privacy is important to us, and we are committed to protecting your data. This Privacy Policy explains how we collect, use, process, and protect your information when you use our platform to search for teachers online and offline.</p>" +

                            "<h3>When You Post Requirements as a Student</h3>" +
                            "<p>When you post your requirements as a student, we collect and process certain information to match you with suitable teachers. The data we collect includes but is not limited to:</p>" +
                            "<ul>" +
                            "<li>Your name and contact details</li>" +
                            "<li>Your location (if applicable)</li>" +
                            "<li>The subject or course you are looking for</li>" +
                            "<li>Your preferred mode of learning (online or offline)</li>" +
                            "<li>Any additional information you choose to share</li>" +
                            "</ul>" +
                            "<p>We use this information to help you connect with relevant teachers who meet your criteria while ensuring that your data remains secure.</p>" +

                            "<h3>I Want to Add My Account</h3>" +
                            "<p>To create an account on our platform, you will need to provide certain personal information, including:</p>" +
                            "<ul>" +
                            "<li>Full name</li>" +
                            "<li>Email address</li>" +
                            "<li>Phone number</li>" +
                            "<li>Password</li>" +
                            "</ul>" +
                            "<p>Your account information allows you to post requirements, browse teachers, and engage with our services securely. You can update or modify your details at any time by accessing your account settings.</p>" +

                            "<h3>I Want to Add Data from Google</h3>" +
                            "<p>We offer an option to integrate data from your Google account to enhance your experience on our platform. When you opt for this feature, we may collect the following information from Google:</p>" +
                            "<ul>" +
                            "<li>Name</li>" +
                            "<li>Email address</li>" +
                            "<li>Profile picture</li>" +
                            "<li>Other publicly available details</li>" +
                            "</ul>" +
                            "<p>Your Google account data is used solely for authentication purposes and to personalize your experience on our platform. You can manage your Google data access permissions in your account settings at any time.</p>" +

                            "<h3>What Data Do We Process?</h3>" +
                            "<p>We process various types of data to ensure the best experience for our users. This includes:</p>" +
                            "<ul>" +
                            "<li><b>Personal Information:</b> Name, email, phone number, location, etc.</li>" +
                            "<li><b>Educational Preferences:</b> Subjects of interest, preferred teachers, learning modes, etc.</li>" +
                            "<li><b>Technical Information:</b> IP address, browser type, device details, etc.</li>" +
                            "<li><b>Communication Records:</b> Messages, feedback, inquiries, and customer support interactions</li>" +
                            "</ul>" +
                            "<p>We do not process sensitive personal data unless explicitly required and with your consent.</p>" +

                            "<h3>Purposes for Which We Process Your Data</h3>" +
                            "<p>We process your data for the following purposes:</p>" +
                            "<ul>" +
                            "<li>To facilitate the connection between students and teachers</li>" +
                            "<li>To provide and improve our services</li>" +
                            "<li>To personalize user experience</li>" +
                            "<li>To authenticate and secure user accounts</li>" +
                            "<li>To comply with legal obligations</li>" +
                            "<li>To analyze and improve platform performance</li>" +
                            "</ul>" +
                            "<p>We ensure that your data is processed lawfully, transparently, and for legitimate business purposes.</p>" +

                            "<h3>How to Request Data Addition</h3>" +
                            "<p>If you wish to add or update any data on our platform, you can do so through the following methods:</p>" +
                            "<ul>" +
                            "<li><b>Self-Service Portal:</b> Log into your account and update your details</li>" +
                            "<li><b>Support Request:</b> Contact our customer support team via <b>[support email/contact form]</b></li>" +
                            "<li><b>Google Data Integration:</b> Authorize Google data addition from your account settings</li>" +
                            "</ul>" +
                            "<p>For any concerns regarding data modifications, you can reach out to us at <b>[support email/contact number]</b>.</p>" +

                            "<h3>Data Security and Retention</h3>" +
                            "<p>We take appropriate security measures to protect your data from unauthorized access, alteration, or loss. Your data is retained only as long as necessary for the purposes stated in this policy, after which it is securely deleted.</p>" +

                            "<h3>Third-Party Sharing and Disclosure</h3>" +
                            "<p>We do not sell or share your personal data with third parties without your consent, except when required by law or in cases where third-party services are essential for our platform’s functionality.</p>" +

                            "<h3>Your Rights and Choices</h3>" +
                            "<p>You have the following rights regarding your data:</p>" +
                            "<ul>" +
                            "<li><b>Access and Correction:</b> You can view and edit your data anytime.</li>" +
                            "<li><b>Data Deletion:</b> Request the deletion of your data by contacting us.</li>" +
                            "<li><b>Withdraw Consent:</b> Revoke permission for data processing at any time.</li>" +
                            "<li><b>Data Portability:</b> Request a copy of your data in a readable format.</li>" +
                            "</ul>" +

                            "<h3>Updates to This Privacy Policy</h3>" +
                            "<p>We may update this Privacy Policy periodically to reflect changes in our practices. We encourage you to review this page regularly to stay informed about how we protect your data.</p>";

        }



        aboutTextView.setText(Html.fromHtml(aboutContent));
    }


}